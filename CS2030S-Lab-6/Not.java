import cs2030s.fp.Memo;

class Not implements Cond {
  private Memo<Cond> val;

  public Not(Cond val) {
    this.val = Memo.from(() -> val);
  }

  @Override
  public boolean eval() {
    return !this.val.get().eval();
  }

  @Override
  public String toString() {
    return "!(" + this.val.get() + ")";
  }

  @Override
  public Cond neg() {
    return this.val.get();
  }
}