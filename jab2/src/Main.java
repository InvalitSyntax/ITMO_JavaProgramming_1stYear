import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Battle;
import mypokemons.*;
public class Main {
    /*
     * https://pokemondb.net/pokedex/lapras
     * 
     */

    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Lapras("Лапрасик", 2);
        Pokemon p2 = new Snorunt("Индеец", 2);
        Pokemon p3 = new Froslass("Ледяной покровитель", 2);
        Pokemon p4 = new Cleffa("Шлепа", 2);
        Pokemon p5 = new Clefairy("Шлепыч", 2);
        Pokemon p6 = new Clefable("Шлепанович", 2);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p6);

        b.addFoe(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.go();
    }
}
