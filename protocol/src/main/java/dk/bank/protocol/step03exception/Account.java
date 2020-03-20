package dk.bank.protocol.step03exception;

public class Account {
    final Long id;
    final String name;

    public Account(Long id, String name) {
        if(id==null || id==0)throw new IllegalArgumentException("id cannot be missing or 0");
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String csv() {
        final String csv;
        csv= id.toString() + ';' + name;
        return csv;
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
