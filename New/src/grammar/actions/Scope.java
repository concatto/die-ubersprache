package grammar.actions;

public enum Scope {
  private boolean function = false;
  private String scope;
  private Stack< Pair <String,Integer> > scopes = new Stack<>();
  private int level;
  private HashMap<String, Symbol> tableScope = new HashMap<>();


  public generateScope(label){
    this.scopes.push(new Pair(label, this.level));
    this.level = this.level + 1;
  }

  public searchIdScope(identifier){

  }
}
