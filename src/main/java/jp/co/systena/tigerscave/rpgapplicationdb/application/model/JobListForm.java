package jp.co.systena.tigerscave.rpgapplicationdb.application.model;

import java.util.List;
import javax.validation.Valid;

public class JobListForm {

  @Valid
  private List<Job> jobList;

  public List<Job> getItemList() {
    return jobList;
  }

  public void setItemList(List<Job> jobList) {
    this.jobList = jobList;
  }

}
