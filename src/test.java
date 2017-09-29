import java.util.*;


class MyMap<K,V> implements Map{

    private ArrayList<ArrayList<AbstractMap.SimpleEntry<K,V>>> table;
    private int capacity;

    public MyMap(int capacity){
        table = new ArrayList<>();
        for(int i = 0; i < capacity; i++){
            table.add(new ArrayList<>());
        }

        this.capacity = capacity;
    }

    public MyMap(){
        this(100);
    }

    private int getHashIndex(Object key){
        return Math.abs(key.hashCode() % capacity);
    }

    @Override
    public int size() {
        int res = 0;
        for (ArrayList arr : table) {
            res += arr.size();
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        ArrayList<AbstractMap.SimpleEntry<K,V>> cell = table.get(getHashIndex(key));
        for(AbstractMap.SimpleEntry<K,V> pair : cell){
            if( pair.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {

        for(ArrayList<AbstractMap.SimpleEntry<K,V>> cell : table) {
            for (AbstractMap.SimpleEntry<K, V> pair : cell) {
                if (pair.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object get(Object key) {

        ArrayList<AbstractMap.SimpleEntry<K,V>> cell = table.get(getHashIndex(key));
        for(AbstractMap.SimpleEntry<K,V> pair : cell){
            if(pair.getKey().equals(key)){
                return pair.getValue();
            }
        }

        return null;
    }



    @Override
    public Object put(Object key, Object value) {
        ArrayList<AbstractMap.SimpleEntry<K,V>> cell = table.get(getHashIndex(key));

        for(AbstractMap.SimpleEntry<K,V> pair : cell){
            if( pair.getKey().equals(key)) {
                Object old_value = pair.getValue();
                cell.remove(pair);
                cell.add(new AbstractMap.SimpleEntry<>((K)key, (V)value));
                return old_value;
            }
        }

        cell.add(new AbstractMap.SimpleEntry<>((K)key, (V)value));
        return null;

    }

    @Override
    public Object remove(Object key) {
        int index = key.hashCode() % capacity;
        ArrayList<AbstractMap.SimpleEntry<K,V>> cell = table.get(index);
        for(int i = 0; i < cell.size(); i++){
            if(cell.get(i).getKey().equals(key)){
                Object removed_value = cell.get(i).getValue();
                cell.remove(i);
                return removed_value;
            }
        }
        return null;
    }

    @Override
    public void clear() {

        table.clear();
        for(int i = 0; i < capacity; i++){
            table.add(new ArrayList<>());
        }

    }

    @Override
    public void putAll(Map m) {
        for(Object key : m.keySet()){
            this.put(key, m.get(key));
        }
    }

    @Override
    public Set keySet() {
        Set<K> result = new HashSet<>();

        for( ArrayList<AbstractMap.SimpleEntry<K,V>> cell : table){
            for(AbstractMap.SimpleEntry<K,V> pair : cell){
                result.add( pair.getKey() );
            }
        }

        return result;
    }

    @Override
    public Collection values() {
        Collection<V> result = new ArrayList<>();

        for( ArrayList<AbstractMap.SimpleEntry<K,V>> cell : table){
            for(AbstractMap.SimpleEntry<K,V> pair : cell){
                result.add( pair.getValue() );
            }
        }


        return result;
    }

    @Override
    public Set<Entry> entrySet() {
        Set<Map.Entry> result = new HashSet<>();

        for( ArrayList<AbstractMap.SimpleEntry<K,V>> cell : table){
            for(AbstractMap.SimpleEntry<K,V> pair : cell){
                result.add( pair );
            }
        }

        return result;
    }

}

class TestMyMap{


    public static void startTest(){

        if (testPut()){
            System.out.println("Test put passed");
        }else{
            System.out.println("Test put failed");
        }

        if (testGet()){
            System.out.println("Test get passed");
        }else{
            System.out.println("Test get failed");
        }

        if (testContainsKey()){
            System.out.println("Test ContainsKey passed");
        }else{
            System.out.println("Test ContainsKey failed");
        }


        if (testContainsValue()){
            System.out.println("Test ContainsValue passed");
        }else{
            System.out.println("Test ContainsValue failed");
        }

        if (testRemove()){
            System.out.println("Test Remove passed");
        }else{
            System.out.println("Test Remove failed");
        }

        if (testPutAll()){
            System.out.println("Test PutAll passed");
        }else{
            System.out.println("Test PutAll failed");
        }

        if (testKeySet()){
            System.out.println("Test KeySet passed");
        }else{
            System.out.println("Test KeySet failed");
        }

        if (testValues()){
            System.out.println("Test Values passed");
        }else{
            System.out.println("Test Values failed");
        }

        if (testEntrySet()){
            System.out.println("Test Entry set passed");
        }else{
            System.out.println("Test Entry set failed");
        }


    }

    public static boolean testPut(){
        MyMap<String, String> map = new MyMap<>();
        String key = "JOe";
        String value = "LOu";
        map.put(key,value);

        return map.containsKey(key) && value.equals(map.get(key));
    }

    public static boolean testGet(){
        MyMap<String, String> map = new MyMap<>();
        
        if( map.get("Hello") != null ){
            return false;
        }

        map.put("JOe","LOu");
        if(! map.get("JOe").equals("LOu") ){
            return false;
        }

        return true;


    }

    public static boolean testContainsKey(){
        MyMap<String, String> map = new MyMap<>();
        
        if ( map.containsKey("Hello") ) {
            return false;
        }

        map.put("Hello", "Hello");

        if ( ! map.containsKey("Hello") ) {
            return false;
        }


        return true;

    }

    public static boolean testContainsValue(){
        MyMap<String, String> map = new MyMap<>();
        if ( map.containsValue("Hello") ) {
            return false;
        }

        map.put("Hello", "Hello");

        if ( ! map.containsValue("Hello") ) {
            return false;
        }

        return true;

    }

    public static boolean testRemove(){
        MyMap<String, String> map = new MyMap<>();
        if ( map.remove("Hello") != null ) {
            return false;
        }

        map.put("Hello", "Hello");

        if ( map.remove("Hello") == null) {
            return false;
        }


        return true;

    }

    public static boolean testPutAll(){
        MyMap<String, String> map = new MyMap<>();
        HashMap<String, String> second = new HashMap<>();
        String key = "JOe";
        String value = "POl";
        second.put(key,value);

        map.putAll(second);

        return map.get(key).equals(value);
    }

    public static boolean testKeySet(){
        MyMap<String, String> map = new MyMap<>();

        String key = "JOe";
        String value = "POl";

        map.put(key, value);

        return map.keySet().contains(key);

    }

    public static boolean testValues(){
        MyMap<String, String> map = new MyMap<>();

        String key = "JOe";
        String value = "POl";

        map.put(key, value);

        return map.values().contains(value);

    }

    public static boolean testEntrySet(){
        MyMap<String, String> map = new MyMap<>();
        String key = "JOe";
        String value = "POl";

        map.put(key, value);

        return map.entrySet().contains(new AbstractMap.SimpleEntry(key, value));

    }

}




public class test{

    public static void main(String[] args){

        TestMyMap.startTest();

        Map<String, String> m = new Hashtable<>();
//
////        m.startTest();
//
        m.put("hello", "world");
//        m.put("hello4", "world2");
//        m.put("hello5", "world3");
//
//        Set<Map.Entry> a = m.entrySet();
//
//        for(Map.Entry mm : a){
//            System.out.println(mm.getKey());
//            System.out.println(mm.getValue());
//        }

//        Map<String, String> s = new MyMap<>();
//        Map<String, Integer> in = new MyMap<>();
//
//        s.put("awd", "awd");
//        in.put("dwa",123);
//
//        s.putAll(in);
//
//        System.out.println(s.get("awd"));
//        System.out.println(s.get("dwa"));



    }




}
