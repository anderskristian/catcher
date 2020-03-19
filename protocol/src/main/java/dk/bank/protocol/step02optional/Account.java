package dk.bank.protocol.step02optional;

public class Account {
    final Long id;
    final String name;

    public Account(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("id=").append(id);
        if(name!=null)sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
