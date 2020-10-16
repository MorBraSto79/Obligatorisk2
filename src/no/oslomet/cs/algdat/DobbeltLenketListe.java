package no.oslomet.cs.algdat;

import java.util.Comparator;
import java.util.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class DobbeltLenketListe<T> implements Liste<T> {

    private static final class Node<T>{
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste){
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        public T verdi(){
            return verdi;
        }

        private Node(T verdi){
            this(verdi, null, null);
        }

        private Node<T> forrige() {
            return forrige;
        }

        private Node<T> neste() {
            return neste;
        }

        public void neste(Node<T> n) {
            neste = n;
        }

        public void forrige(Node<T> n) {
            forrige = n;
        }

        public void verdi(T v) {
            verdi = v;
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

        this();
        Objects.requireNonNull(a, "a er en null-tabell!");
        if (a.length == 0) {
            return;
        }
        int start = 0;
        while (a[start] == null){
            if (start >= a.length - 1){
                return;
            }
            start++;
        }

        hode = new Node<T>(a[start]);
        hale = hode;
        antall = 1;
        for (int i = start + 1; i < a.length; i++){
            if (a[i] == null){
                continue;
            }
            Node<T> ny = new Node<T>(a[i], hale, null);
            hale.neste(ny);
            ny.forrige(hale);
            hale = ny;
            antall++;
        }

        //throw new UnsupportedOperationException();
    }

    private void fratilKontroll(int antall, int fra, int til){
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");
        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");
        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(antall,fra, til);
        Liste<T> sub = new DobbeltLenketListe<>();
        for (int i = fra; i < til; i++){
            sub.leggInn(hent(i));
        }
         return sub;
        //throw new UnsupportedOperationException();
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

        Objects.requireNonNull(verdi, "Vi kan ikke ha med null-verdier");
        if (0 > indeks || indeks > antall){
            throw new IndexOutOfBoundsException();
        } else if (tom()) {
            Node<T> node = new Node<T>(verdi);
            hode = node;
            hale = node;
        } else if (indeks == 0){
            Node<T> node =  new Node<T>(verdi, null, hode);
            hode.forrige(node);
            hode = node;
        } else if (indeks == antall) {
            Node<T> node = new Node<T>(verdi, hale, null);
            hale.neste(node);
            hale = node;
        } else {
            Node<T> m = finnNode(indeks - 1);
            Node<T> n = m.neste();
            Node<T> node = new Node<T>(verdi, m, n);
            m.neste(node);
            n.forrige(node);
        }
        antall++;
        endringer++;


        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {

        if(indeksTil(verdi) != -1){
            return true;
        }
        return false;

        //throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {

        indeksKontroll(indeks, false);
        Node<T> node = finnNode(indeks);
        return node.verdi();

        //throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {

        Node<T> node = this.hode;
        if (this.hode == null){
            return -1;
        } else if (node.verdi().equals(verdi)){
            return 0;
        }

        int indeks = 0;
        while (node.neste() != null){
            node = node.neste();
            indeks++;
            if(node.verdi().equals(verdi)){
                return indeks;
            }
        }
        return -1;
        //throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi);
        indeksKontroll(indeks, false);
        Node<T> node = finnNode(indeks);
        T verdi = node.verdi();
        node.verdi(nyverdi);
        endringer++;
        return verdi;

        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {

        Node<T> node = finnNode(verdi);
        if (node == null) {
            return false;
        }

        if (node.forrige() != null){
            node.forrige().neste(node.neste());
        } else {
            this.hode = node.neste();
        }

        if (node.neste() != null) {
            node.neste().forrige(node.forrige());
        } else {
            this.hale = node.forrige();
        }

        node = null;
        antall--;
        endringer++;
        return true;

        //throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {

        Node<T> node = finnNode(indeks);
        T verdi = node.verdi();
        if (node.forrige() != null) {
            node.forrige().neste(node.neste());
        } else {
            this.hode = node.neste();
        }
        if (node.neste() != null) {
            node.neste().forrige(node.forrige());
        } else {
            this.hale = node.forrige();
        }

        node =  null;
        antall--;
        endringer++;
        return verdi;

        //throw new UnsupportedOperationException();
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

        return new DobbeltLenketListeIterator();

        //throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {

        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);

        // throw new UnsupportedOperationException();
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

            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;

            //throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){

            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T verdi = denne.verdi();
            denne = denne.neste();
            return verdi;

            //throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }
    // hjelpemtode
    private Node<T> finnNode(int indeks) {

        indeksKontroll(indeks, false);
        if (indeks < antall / 2) {
            Node<T> node = this.hode;
            while (node.neste() != null && indeks > 0) {
                node = node.neste();
                indeks--;
            }
            return node;
        }
        Node<T> node = this.hale;
        int telling = 1;
        while (node.forrige() != null && antall - telling > indeks) {
            node = node.forrige();
            telling++;
        }

        return node;

        //throw new UnsupportedOperationException();
    }
    private Node<T> finnNode(T verdi) {

        Node<T> node = this.hode;
        if (node == null) {
            return null;
        }
        if (node.verdi().equals(verdi)) {
            return node;
        }
        while (node.neste() != null) {
            node = node.neste();
            if (node.verdi().equals(verdi)) {
                return node;
            }
        }
        return null;
    }
} // class DobbeltLenketListe

