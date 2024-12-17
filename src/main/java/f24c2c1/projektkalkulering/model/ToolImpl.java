package f24c2c1.projektkalkulering.model;

public class ToolImpl implements Tool {
    private long id;
    private String name;
    private double value;

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public double getValue() { return value; }

    @Override
    public void setValue(double value) { this.value = value; }
}
