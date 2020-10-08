package no.oslomet.cs.algdat;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public interface Beholder<T> extends Iterable<T> {

    public boolean leggInn(T t);

    public boolean inneholder(T t);

    public boolean fjern(T t);

    public int antall();

    public boolean tom();

    public void nullstill();

    public Iterator<T> iterator();

    default boolean fjernHvis(Predicate<? super T> p){
        Objects.requireNonNull(p);

        boolean fjernet = false;
        for (Iterator<T> i = iterator(); i.hasNext();){
            if (p.test(i.next())){
                i.remove();
                fjernet = true;
            }
        }
        return fjernet;
    }
}
