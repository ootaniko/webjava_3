package jp.co.systena.tigerscave.rpgapplicationdb.application.model;

public class JobForm {

  private int jobId;
  private String name;
  private boolean goNext;

  public int getJobId() {
    return jobId;
  }
  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public boolean getGoNext() {
    return goNext;
  }
  public void setGoNext(boolean goNext) {
    this.goNext = goNext;
  }

}
