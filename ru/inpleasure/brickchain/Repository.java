import java.util.List;

public interface Repository
{
    Brick loadBrick(int identificator);
    void saveBrick(Brick brick);
    List<Brick> getBricks();
    List<Integer> getIdentificators();
}
