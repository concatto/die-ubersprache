package grammar.actions;

import backend.Symbol;
import backend.SymbolTable;
import backend.Type;
import grammar.SemanticError;

public class Scope {
  private Stack<Integer> scopes = new Stack<>();
  private Map<Pair<String,Integer>, Symbol> tableScope = new HashMap<>();

  public void addScope() {
    this.scopes.push(scopes.size());
  }

  public void addForScope(String id) {
    //iniserir um symbolo a partir do #701 declaração do "for"
    this.scopes.push(scopes.size());
    Symbol s = null;
    Symbol actual = null;
    for(int i = scopes.size()-1; i >= 0; i--) {
      s = tableScope.get(new Pair<>(id, i));
      if(s != null) {
        actual = s;
      }
    }
    if(actual == null) {
      insertSymbolScope(...);
    }
  }

  public Symbol searchUsedId(String id) {
    Symbol s = null;
    for(int i = scopes.size()-1; i >= 0; i--) {
      s = tableScope.get(new Pair<>(id, i));
      if(s != null) {
        return s;
      }
    }
    throw new SemanticException("Variável "+ id +" não declarada!");
  }

  public void removeVarScope(String id) {
    Symbol s = null;
    for(int i = scopes.size()-1; i >= 0; i--) {
      s = tableScope.get(new Pair<>(id, i));
      if(s != null) {
        tableScope.remove(new Pair<>(id, i)); //NÃO SEI EXATAMENTE SE ISSO FUNCIONARIA
      }
    }
  }

  public Symbol insertSymbolScope(String id, Int type, boolean function, boolean used, boolean parameter, int size, int scope, int depth, int parameterPosition) {
    //insere um symbol na symboltable
  }

  public void removeScope() {
    removeVarScope();
    scopes.pop();
  }
}
