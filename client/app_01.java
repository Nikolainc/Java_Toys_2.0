package client;

import java.util.Scanner;

import core.model.Toy;
import core.presenter.Presenter;
import core.service.DataManager;
import core.service.FileManager;
import core.service.IDataManager;
import core.service.IDataProvider;
import core.view.ConsoleView;
import core.view.IView;

public class app_01 {

    private app_01() {}

    public static void ButtonClick() {

        System.out.print("\033[H\033[J");
        IView view = new ConsoleView();
        IDataProvider<Toy> provider = new FileManager<>();
        IDataManager<Toy> manager = new DataManager<>(provider, "Data.txt", "Pass.txt", "Count.txt");
        Presenter<Toy> presenter = new Presenter<>(view, manager);
        System.out.print("\033[H\033[J");
        int money = 100;

        try (Scanner in = new Scanner(System.in)) {

            while (true) {

                if(presenter.isAdmin()) { //пароль admin

                    System.out.println("0 - Выход из режима админа 1 - Вывести всё  2 - Добавить продукт 3 - Изменить кол-во товара 4 - Изменить вес товара\n 5 - Выход из программы");
                    String key = in.next();
                    System.out.print("\033[H\033[J");
                    switch (key) {

                        case "0":
                            presenter.exitAdmin();
                            break;
                        
                        case "1":
                            presenter.gAllProducts();
                            break;

                        case "2":
                            presenter.addNewProduct();
                            break;

                        case "3":
                            presenter.ChangeCountProducts();
                            break;

                        case "4":
                            presenter.ChangeWeightProduct();
                            break;
                        
                        case "5":
                            presenter.Quit();
                            System.exit(0);
                            break;

                        default:

                            System.out.println("Такой команды нет");
                            break;

                    }

                } else {

                    System.out.println(
                            "0 - Войти в режим админа \n1 - Розыгрыш  игрушек = 10р \n2 - Вывести список выйгранных игрушек\n3 - Показать все игрушки\n4 - Выход из программы");
                    String key = in.next();
                    System.out.print("\033[H\033[J");
                    switch (key) {

                        case "0":
                            presenter.enterAdmin();
                            break;
                        
                        case "1":
                            if(money >= 10) {
                                money -= 10;
                                System.out.println(money + " руб осталось");
                                presenter.startPlay();

                            } else {

                                System.out.println("Денег не осталось");

                            }
                            break;

                        case "2":
                            
                            presenter.getToysWinner();

                            break;

                        case "3":
                            presenter.gAllProducts();
                            break;

                        case "4":
                            presenter.Quit();
                            System.exit(0);
                            break;

                        default:

                            System.out.println("Такой команды нет");
                            break;

                    }


                }
                
            }
        }
    }
}
