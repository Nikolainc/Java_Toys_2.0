package core.service;

import java.util.ArrayList;
import java.util.List;

import core.model.Toy;
import core.model.Count;

public class DataManager<T extends Toy> implements IDataManager<T> {

    private IDataProvider<T> provider;
    private String dataFileName;
    private String passDataFileName;
    private String countDataFileName;
    private StringBuilder stringBuilder;

    public DataManager (IDataProvider<T> provider, String dataName, String passDataName, String countDataFile) {

        this.provider = provider;
        this.dataFileName = dataName;
        this.passDataFileName = passDataName;
        this.countDataFileName = countDataFile;
        this.stringBuilder = new StringBuilder();

    }

    public List<T> getGoodList() {

        if(this.provider.isEmpty(this.dataFileName)) {

            System.out.println(dataFileName + " File %s is Empty");
            return null;

        } else {

            List<T> list = new ArrayList<>();
            String[] data = this.provider.load(dataFileName).split("\n");

            for (String item : data) {

                String[] rawData = item.split(";");
                
                list.add((T) new Toy(Integer.parseInt(rawData[0]), rawData[1], Float.parseFloat(rawData[2])));

            }
            
            return list;

        }

    }

    public boolean saveGoodList(List<T> goods) {

        for (int i = 0; i < goods.size() - 1; i++) {

            var item = goods.get(i);
            stringBuilder.append(item.gId() + ";" + item.gName() + ";" + item.gWeight() + "\n");

        }

        stringBuilder.append(goods.get(goods.size() - 1).gId() + ";" + goods.get(goods.size() - 1).gName() + ";" + goods.get(goods.size() - 1).gWeight() + "\n");
        String rawData = stringBuilder.toString();
        stringBuilder.setLength(0);
        return this.provider.save(rawData, dataFileName);

    }

    public boolean savePass(String pass) {

        if(isEmpty(passDataFileName)) {

            String hash = Integer.toString(pass.hashCode());
            return this.provider.save(hash, passDataFileName);

        } else {

            return Integer.toString(pass.hashCode()).equals(this.provider.load(passDataFileName).replaceAll("\n", ""));

        }

    }

    public List<Count> getCountFeed() {

        if (this.provider.isEmpty(this.countDataFileName)) {

            System.out.println(countDataFileName + " File %s is Empty");
            return null;

        } else {

            List<Count> list = new ArrayList<>();
            String[] data = this.provider.load(countDataFileName).split("\n");

            for (String item : data) {
                
                String[] rawData = item.split(";");
                list.add(new Count(Integer.parseInt(rawData[0]), Integer.parseInt(rawData[1])));

            }

            return list;

        }

    }

    public boolean saveCountData(List<Count> data) {

        for (int i = 0; i < data.size() - 1; i++) {

            var item = data.get(i);
            stringBuilder.append(item.gId() + ";" + item.gCount() + "\n");

        }

        stringBuilder.append(data.get(data.size() - 1).gId() + ";" + data.get(data.size() - 1).gCount() + ";" + "\n");

        String rawData = stringBuilder.toString();
        stringBuilder.setLength(0);

        return this.provider.save(rawData, countDataFileName);

    }

    public boolean isEmpty(String fileName) {

        return this.provider.isEmpty(fileName);

    }

}
