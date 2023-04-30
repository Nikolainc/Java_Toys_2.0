package core.service;

import core.model.Toy;

public interface IDataProvider<T extends Toy> {

    public String load(String fileName);

    public boolean save(String data, String fileName);

    public boolean isEmpty(String fileName);
    
}
