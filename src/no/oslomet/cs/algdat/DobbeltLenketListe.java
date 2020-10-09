package no.oslomet.cs.algdat;

import java.util.Comparator;
import java.util.Objects;
import java.util.Iterator;

public class DobbeltLenketListe<T> implements Liste<T> {

    private static final class Node<T>{
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste){
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi){
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {


        //throw new UnsupportedOperationException();
    }

    public DobbeltLenketListe(T[] a) {


        //throw new UnsupportedOperationException();
    }

    public Liste<T> subliste(int fra, int til){
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {

        return antall;
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean tom() {

        if (antall == 0 && hode == null && hale == null){
            return true;
        }
        else{
            return false;
        }
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Null-verdier er ikke tillat");

        if (tom()){
            hode = hale = new Node<T>(verdi, null, null);
        }
        else{
            hale = hale.neste = new Node<T>(verdi, hale, null);
        }

        antall++;
        endringer++;

        return true;

        //throw new UnsupportedOperationException();
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder streng = new StringBuilder();

        streng.append('[');     //legger til "start" klammen

        if(!tom()){             // hvis lenken er tom går vi videre, hvis ikke, så looper vi gjennom listen
            streng.append(hode.verdi);
            for (Node<T> i = hode.neste; i != null; i = i.neste){    // så lenge noden ikke perker på null, legger vi til komma, mellomrom og nodens verdi
                streng.append(',').append(' ').append(i.verdi);
            }
        }

        streng.append(']');     // legger til "Slutt" klammen uten komma bak siste tall
        return streng.toString();
        //throw new UnsupportedOperationException();
    }

    public String omvendtString() {
        StringBuilder streng = new StringBuilder();

        streng.append('[');     //legger til "start" klammen

        if(!tom()){             // hvis lenken er tom går vi videre, hvis ikke, så looper vi baklengs gjennom listen
            streng.append(hale.verdi);
            for (Node<T> i = hale.forrige; i != null; i = i.forrige){    // så lenge noden ikke perker på null, legger vi til komma, mellomrom og nodens verdi
                streng.append(',').append(' ').append(i.verdi);
            }
        }

        streng.append(']');     // legger til "Slutt" klammen uten komma bak siste tall
        return streng.toString();
        //throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe

