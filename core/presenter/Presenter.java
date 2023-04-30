package core.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.model.Count;
import core.model.Toy;
import core.service.IDataManager;
import core.view.IView;

public class Presenter<T extends Toy> {

    private IView view;
    private IDataManager<T> manager;
    private List<T> productFeed;
    private List<Count> productCountFeed;
    private boolean admin;
    private List<T> winnerListItem;

    public Presenter(IView view, IDataManager<T> manager) {

        this.view = view;
        this.manager = manager;
        this.productFeed = this.manager.getGoodList();
        this.productCountFeed = this.manager.getCountFeed();
        this.admin = false;
        this.winnerListItem = new ArrayList<>();

    }

    public boolean isAdmin() {

        return this.admin;

    }

    public void exitAdmin() {

        this.admin = false;
        this.view.set("Режим админа выключен");

    }

    public void enterAdmin() {

        this.view.set("Введите пароль админа: ");
        String pass = this.view.get();

        if(this.manager.savePass(pass)) {

            this.view.set("Режим админа включен");
            this.admin = true;

        } else {

            this.view.set("Пароль не верный");

        }

    }

    public void gAllProducts() {

        printProducts();

    }

    private void saveGoodList() {

        if(this.manager.saveGoodList(this.productFeed)) {

            this.view.set("Сохранено!");

        } else {

            this.view.set("Ошибка сохранения!");

        }

    }

    private void saveCountFeed() {

        if(this.manager.saveCountData(this.productCountFeed)) {

            this.view.set("Сохранено!");

        } else {

            this.view.set("Ошибка сохранения!");

        }

    }

    public void addNewProduct() {

        T newGood = createNewProduct();

        if(newGood != null) {

            if(this.productFeed.contains(newGood)) {

                this.view.set("Такой продукт уже существует");

            } else {

                this.productFeed.add(newGood);
                addCountProducts(newGood.gId());

            }
            
        }

    }

    private T createNewProduct() {

        try {
            
            int id = this.productFeed.size();
            this.view.set("Введите название продукта: ");
            String name = this.view.get();
            this.view.set("Введите частоту выпадения от 0.0 до 1.0: ");
            float weight = Float.parseFloat(this.view.get());
            if(weight > 1f || weight < 0f) {

                weight = 0.5f;

            }

            return (T) new Toy(id, name, weight);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    private void addCountProducts(int id) {

        try {

            int idProduct = id;

            if (this.productFeed.stream().anyMatch(element -> element.gId() == idProduct)) {

                this.view.set("Введите кол-во: ");
                int Count;

                try {

                    Count = Integer.parseInt(this.view.get());

                } catch (Exception e) {

                    Count = 1;

                }

                Count newCount = new Count(idProduct, Count);

                if (this.productCountFeed.contains(newCount)) {

                    var prodCount = this.productCountFeed.stream().filter(element -> element.gId() == idProduct)
                            .collect(Collectors.toList());

                    for (Count item : prodCount) {

                        item = newCount;
                        break;
                    }

                } else {

                    this.productCountFeed.add(newCount);

                }

            } else {

                this.view.set("Такой продукт не найден");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void ChangeWeightProduct() {

        try {

            printProducts();

            this.view.set("Введите id продукта для изменения веса: ");
            int idProduct = Integer.parseInt(this.view.get());

            if (this.productFeed.stream().anyMatch(element -> element.gId() == idProduct)) {

                this.view.set(this.productFeed.get(idProduct).toString());

                this.view.set("Введите новый вес от 0.0 до 1.0: ");
                float weight;

                try {

                    weight = Float.parseFloat(this.view.get());

                } catch (Exception e) {

                    weight = 0.5f;

                }

                if(weight > 1f || weight < 0f) {

                    weight = 0.5f;

                } 
                var prod = this.productFeed.get(idProduct);
                var newProd = (T) new Toy(prod.gId(), prod.gName(), weight);
                this.productFeed.set(idProduct, newProd);

            } else {

                this.view.set("Такой продукт не найден");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void printProducts() {

        for (int i = 0; i < this.productFeed.size(); i++) {
            
            this.view.set(this.productFeed.get(i).toString() + " " + this.productCountFeed.get(i).toString());

        }

    }

    public void ChangeCountProducts() {

        try {

            printProducts();

            this.view.set("Введите id продукта для изменения кол-ва: ");
            int idProduct = Integer.parseInt(this.view.get());

            if (this.productFeed.stream().anyMatch(element -> element.gId() == idProduct)) {

                this.view.set("Введите новое кол-во: ");
                int Count;

                try {

                    Count = Integer.parseInt(this.view.get());

                } catch (Exception e) {

                    Count = 1;

                }
                
                Count newCount = new Count(idProduct, Count);

                if(this.productCountFeed.contains(newCount)) {

                    this.productCountFeed.set(idProduct, newCount);

                } else {

                    this.productCountFeed.add(newCount);

                }

                
            } else {

                this.view.set("Такой продукт не найден");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void Quit() {

        saveGoodList();
        saveCountFeed();

    }

    public void startPlay() {

        float totalWeigt = 0.0f;

        for (T item : this.productFeed) {

            totalWeigt += item.gWeight();

        }

        int id = 0;

        for (float rand = (float)Math.random() * totalWeigt; id < this.productFeed.size() - 1; ++id) {
            rand -= this.productFeed.get(id).gWeight();
            if (rand <= 0.0)
                break;
        }

        var prod = this.productFeed.get(id);

        if(this.productCountFeed.get(id).gCount() <= 0) {

            this.view.set("Проиграл!");

        } else {

            this.winnerListItem.add(prod);
            this.view.set("Победа!");
            this.view.set(prod.toString());
            this.productCountFeed.set(id, new Count(id, this.productCountFeed.get(id).gCount() - 1));

        }

    }

    public void getToysWinner() {

        this.view.set("Вы выйграли: ");

        for (T item : this.winnerListItem) {

            this.view.set(item.toString());
            
        }

    }
    
}