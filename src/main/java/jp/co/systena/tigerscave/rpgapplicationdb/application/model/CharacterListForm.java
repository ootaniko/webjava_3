package jp.co.systena.tigerscave.rpgapplicationdb.application.model;

import java.util.List;
import javax.validation.Valid;

public class CharacterListForm {

  @Valid
  private List<Character> characterList;

  public List<Character> getCharacterList() {
    return characterList;
  }

  public void setCharacterList(List<Character> characterList) {
    this.characterList = characterList;
  }


}
