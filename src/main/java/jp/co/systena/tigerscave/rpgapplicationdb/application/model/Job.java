package jp.co.systena.tigerscave.rpgapplicationdb.application.model;

import javax.validation.constraints.Pattern;

public class Job {

  @Pattern(regexp="^[0-9]*$")
  private int jobId;
  private String jobName;
  private String characterName;
  private String battleMessage;
  private String healMessage;
  @Pattern(regexp="^[0-9]*$")
  private int commandId;

//  public Job(int jobId, String jobName, String characterName) {
//    this.setJobId(jobId);
//    this.setJobName(jobName);
//    this.setCharacterName(characterName);
//    this.commandId = -1;
//  }

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }

  public int getCommandId() {
    return commandId;
  }

  public void setCommandId(int commandId) {
    this.commandId = commandId;
  }

//  public abstract String battle();
//
//  public abstract String heal();

  public String getBattleMessage() {
    return battleMessage;
  }

  public void setBattleMessage(String battleMessage) {
    this.battleMessage = battleMessage;
  }

  public String getHealMessage() {
    return healMessage;
  }

  public void setHealMessage(String healMessage) {
    this.healMessage = healMessage;
  }

}
