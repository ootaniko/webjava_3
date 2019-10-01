package jp.co.systena.tigerscave.rpgapplicationdb.application.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.rpgapplicationdb.application.model.Character;
import jp.co.systena.tigerscave.rpgapplicationdb.application.model.CommandForm;
import jp.co.systena.tigerscave.rpgapplicationdb.application.model.Job;
import jp.co.systena.tigerscave.rpgapplicationdb.application.model.JobForm;

@Controller // Viewあり。Viewを返却するアノテーション
public class RpgController {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  HttpSession session; // セッション管理

  @RequestMapping(value = "/gametitle", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView index(ModelAndView mav, @Valid JobForm jobForm) {

    session.invalidate();

    mav.setViewName("gametitle");
    return mav;
  }

  @RequestMapping(value = "/gametitle", method = RequestMethod.POST) // URLとのマッピング
  private ModelAndView postgametitle(ModelAndView mav, @Valid JobForm jobForm,
      BindingResult bindingResult, HttpServletRequest request) {

    // result画面から来た場合はgametitle画面を出す
    if (session.getAttribute("life") != null) {
      session.invalidate();
      mav.setViewName("gametitle");
      return mav;
    }

    // 保存されているキャラクターがない場合はキャラクター作成画面を出す
    if(getCharacterList().size() == 0) {
      return new ModelAndView("redirect:/charactermake"); // リダイレクト
    }

    // newgame or continueのチェック
    if (jobForm.getGoNext()) {

      // t_characterをリセットする
      int deleteCount = jdbcTemplate.update("DELETE FROM t_character");

      return new ModelAndView("redirect:/charactermake"); // リダイレクト
    } else {
      return new ModelAndView("redirect:/commandselect"); // リダイレクト
    }

  }

  @RequestMapping(value = "/charactermake", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView getcharactermake(ModelAndView mav, @Valid JobForm jobForm,
      @Valid boolean goNext) {

    mav.addObject("characternum", getCharacterList().size());

    mav.setViewName("charactermake");
    return mav;
  }

  @RequestMapping(value = "/charactermake", method = RequestMethod.POST) // URLとのマッピング
  private ModelAndView postcharactermake(ModelAndView mav, @Valid JobForm jobForm,
      @Valid boolean goNext, BindingResult bindingResult, HttpServletRequest request) {

    int counter = 0;

    // counterの情報がセッションにあれば実行する
    if (session.getAttribute("counter") != null) {
      counter = (int) session.getAttribute("counter");
    }

    // 画面入力値にエラーがない場合
    if (!bindingResult.hasErrors()) {

      if(StringUtils.isEmpty(jobForm.getName())) {
        jobForm.setName("名無しさん");
      }

      // 1行分の値をデータベースにINSERTする
      // SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
      int insertCount = jdbcTemplate.update("INSERT INTO t_character VALUES( ?, ?, ?, ?)", counter,
          jobForm.getName(), jobForm.getJobId(), -1);

    }

    // counterが3未満かつ次キャラ作成がTrueならば再度ジョブ情報の入力を要求する
    if ((counter < 3) && (jobForm.getGoNext())) {
      counter++;
      session.setAttribute("counter", counter);
      return new ModelAndView("redirect:/charactermake"); // リダイレクト
    }

    counter = 0;
    session.setAttribute("counter", counter);
    return new ModelAndView("redirect:/commandselect"); // リダイレクト

  }

  @RequestMapping(value = "/commandselect", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView getcommandselect(ModelAndView mav, @Valid CommandForm commandForm) {

    int counter = 0;

    // counterの情報がセッションにあれば実行する
    if (session.getAttribute("counter") != null) {
      counter = (int) session.getAttribute("counter");
    }

    List<Character> characterList = getCharacterList();
    List<Job> jobList = getJobList();

    mav.addObject("characterName", characterList.get(counter).getCharacterName());
    mav.addObject("jobName", jobList.get(characterList.get(counter).getJobId()).getJobName());

    mav.setViewName("commandselect");
    return mav;
  }

  @RequestMapping(value = "/commandselect", method = RequestMethod.POST) // URLとのマッピング
  private ModelAndView postcommandselect(ModelAndView mav, @Valid CommandForm commandForm,
      BindingResult bindingResult, HttpServletRequest request) {

    int command = commandForm.getCommandId();
    int counter = 0;

    // counterの情報がセッションにあれば実行する
    if (session.getAttribute("counter") != null) {
      counter = (int) session.getAttribute("counter");
    }
    List<Character> characterList = getCharacterList();
    // 画面入力値にエラーがない場合
    if (!bindingResult.hasErrors()) {

      // 1行分の値でデータベースをUPDATEする
      // SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
      int updateCount = jdbcTemplate
          .update("UPDATE t_character SET command_id = ? WHERE character_id = ?", command, counter);
    }

    // counterがキャラクター数より大きければresultを表示する
    if (counter >= characterList.size() - 1) {
      counter = -1;
      session.setAttribute("counter", counter);
      return new ModelAndView("redirect:/result"); // リダイレクト
    }

    // counterを1進め、再度コマンドの入力を要求する
    counter++;
    session.setAttribute("counter", counter);
    return new ModelAndView("redirect:/commandselect"); // リダイレクト
  }

  @RequestMapping(value = "/result", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView getresult(ModelAndView mav) {

    List<Character> characterList = getCharacterList();
    List<Job> jobList = getJobList();

    // 敵の体力
    int life = 100;
    if (session.getAttribute("life") != null) {
      if ((int) session.getAttribute("life") > 0) {
        life = (int) session.getAttribute("life");
      }
    }

    Job job = null;
    String result = "";
    int damage = 0;

    // コマンドに従い各キャラクターの行動の結果をresultに追加する
    for (int i = 0; i < characterList.size(); i++) {
      result += characterList.get(i).getCharacterName();
      if (characterList.get(i).getCommandId() == 0) {
        result += jobList.get(characterList.get(i).getJobId()).getBattleMessage();
        // 攻撃を行ったらダメージを10加算
        damage += 10;
      } else if (characterList.get(i).getCommandId() == 1) {
        result += jobList.get(characterList.get(i).getJobId()).getHealMessage();
      }
    }

    // ダメージの総量だけ敵の体力を減少する
    if (life >= damage) {
      life -= damage;
    } else {
      life = 0;
    }

    mav.addObject("result", result);
    mav.addObject("life", life);
    mav.addObject("damage", damage);

    session.setAttribute("life", life);

    mav.setViewName("result");
    return mav;
  }

  /**
   * データベースからジョブデータ一覧を取得する
   *
   * @return
   */
  private List<Job> getJobList() {

    // SELECTを使用してテーブルの情報をすべて取得する
    List<Job> list = jdbcTemplate.query("SELECT * FROM t_job ORDER BY job_id",
        new BeanPropertyRowMapper<Job>(Job.class));

    return list;

  }

  /**
   * データベースからキャラクターデータ一覧を取得する
   *
   * @return
   */
  private List<Character> getCharacterList() {

    // SELECTを使用してテーブルの情報をすべて取得する
    List<Character> list = jdbcTemplate.query("SELECT * FROM t_character ORDER BY character_id",
        new BeanPropertyRowMapper<Character>(Character.class));

    return list;
  }

}
