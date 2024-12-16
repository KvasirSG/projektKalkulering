package f24c2c1.projektkalkulering.model;

public class CompetenceImpl implements Competence {
    private long id;
    private String name;

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }
}
