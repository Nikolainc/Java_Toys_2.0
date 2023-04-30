package core.service;

import java.util.List;

import core.model.Count;
import core.model.Toy;

public interface IDataManager<T extends Toy> {

    public List<T> getGoodList();

    public boolean saveGoodList(List<T> goods);

    public boolean savePass(String pass);

    public boolean isEmpty(String fileName);

    public List<Count> getCountFeed();

    public boolean saveCountData(List<Count> productCountFeed);
    
}
