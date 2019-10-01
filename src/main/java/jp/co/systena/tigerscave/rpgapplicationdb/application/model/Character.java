package jp.co.systena.tigerscave.rpgapplicationdb.application.model;

public class Character {

  private int characterId;
  private String characterName;
  private int jobId;
  private int commandId;

  public int getCharacterId() {
    return characterId;
  }
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  public String getCharacterName() {
    return characterName;
  }
  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }

  public int getJobId() {
    return jobId;
  }
  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public int getCommandId() {
    return commandId;
  }
  public void setCommandId(int commandId) {
    this.commandId = commandId;
  }

}
