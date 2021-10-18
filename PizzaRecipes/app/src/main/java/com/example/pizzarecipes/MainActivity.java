package com.example.pizzarecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements PizzaRecyclerAdapter.OnPizzaListener {
    RecyclerView pizzaRecycler;
    ArrayList<PizzaRecyclerItem> pizzaRecyclerList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pizzaRecyclerList.add(new PizzaRecyclerItem("Маргарита", "Соус томатний, сир моцарела та стиглі томати.", "https://pizzahouse.ua/uploads/products/small/margarita.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Чотири сезони", "Соус томатний, сир моцарела, стиглі томати, гриби печериці, апетитна салямі, сир дорблю, що просто тане у роті.", "https://pizzahouse.ua/uploads/products/small/pitstsa-chetyre-sezona.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Вегетаріанська", "Соус томатний, сир моцарела, гриби печериці, стиглі томати, солодкий перець, солодка кукурудза, маринована синя цибуля, маслини, оливки та свіжа зелень.", "https://pizzahouse.ua/uploads/products/small/vegetarianskaya.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Сімейна", "Соус томатний, сир моцарела, соковита шинка та солодка кукурудза, гриби печериці, копчене куряче філе, свіжа зелень.", "https://pizzahouse.ua/uploads/products/small/semeynaya.jpg"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Дракон", "Соус томатний, моцарела, гриби печериці, помідор, салямі, перець халапеньо, французькі трави", "https://pizzahouse.ua/uploads/products/small/drakon.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Гавайська", "Вершковий соус, сир моцарела, солодка кукурудза, копчене куряче філе у поєднанні з тропічним ананасом.", "https://pizzahouse.ua/uploads/products/small/gavayskaya.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Мюнхенська", "Соус гірчичний, ковбаски мисливські, салямі, цибуля, часникове масло, петрушка", "https://pizzahouse.ua/uploads/products/small/myunhenskaya.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Піканте", "Гірчичний соус, сир моцарела, хрусткий бекон, копчене куряче філе з неповторним сиром дорблю.", "https://pizzahouse.ua/uploads/products/small/pikante.jpg"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Теріякі", "Вершковий соус, моцарела, гриби печериці, мариноване куряче філе, соус теріякі, кунжут, цибуля зелена", "https://pizzahouse.ua/uploads/products/small/teriyaki.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Тревізо", "Гірчичний соус, сир моцарела, апетитна салямі, свинина, мисливські ковбаски, стиглі томати, мариновані огірки, палкий гострий перець, трохи зелені та пікантна часникова олія.", "https://pizzahouse.ua/uploads/products/small/trevizo.jpg"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Східна красуня", "Соус томатний, сир моцарела,апетитна салямі та гаряча телятина, стиглі томати, мариновані огірки, палкий гострий перець.", "https://pizzahouse.ua/uploads/products/small/vostochnaya-krasavitsa.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Пепероні", "Соус томатний, сир моцарела у поєднанні із палкою ковбаскою пепероні.", "https://pizzahouse.ua/uploads/products/small/pepperoni.jpg"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Американо", "Соус барбекю, моцарела, помідор, ковбаски мисливські, картопля фрі, петрушка", "https://pizzahouse.ua/uploads/products/small/pitstsa-amerikano.png"));
        pizzaRecyclerList.add(new PizzaRecyclerItem("Кватро ді Карне", "Соус томатний, сир моцарела, хрусткий бекон, апетитна салямі разом із соковитою шинкою, мисливські ковбаски, трохи зелені та пікантна часникова олія, італійські трави", "https://pizzahouse.ua/uploads/products/small/1kvatro-di-karne.jpg"));


        pizzaRecycler = findViewById(R.id.pizzaRecyclerList);
        pizzaRecycler.setHasFixedSize(true);
        pizzaRecycler.setAdapter(new PizzaRecyclerAdapter(pizzaRecyclerList, this));
        pizzaRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemPizzaClick(int position) {
       PizzaRecyclerItem clickedPizzaItem = pizzaRecyclerList.get(position);

        Log.i("click",clickedPizzaItem.getTitle() );
    }
}