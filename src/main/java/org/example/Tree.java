package org.example;
import javax.swing.JOptionPane;
public class Tree implements TreeInterface {
    private Node base;

    public Tree() {
    }

    public void play(){
        if(isEmpty()){
            System.out.println("El arbol esta vacio ingrese un animal: ");
        }else{
            playRecursive(base);
        }
    }

    public int makeQuestion(String pregunta, String dato){
        return JOptionPane.showConfirmDialog(null,
                pregunta + dato + "?",
                "Pregunta",
                JOptionPane.YES_NO_OPTION);
    }

    public int makeQuestion(String pregunta){
        return JOptionPane.showConfirmDialog(null,
                pregunta + "?",
                "Pregunta",
                JOptionPane.YES_NO_OPTION);
    }

    public void quieresJugarOtraVez(){
        if(makeQuestion("Quieres jugar de nuevo")==JOptionPane.YES_OPTION){
            play();
        }
    }

    public void showMessage(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showNewAnimalAdd(String animal, String caracteristica){
        JOptionPane.showMessageDialog(null, "Nuevo animal: " + animal + "\nCaracterística: " + caracteristica,
                "Nuevo animal añadido", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean capturarDatosAnimal(String[] nombreAnimal, String[] caracteristicaAnimal) {
        // Pedir el nombre del animal
        nombreAnimal[0] = JOptionPane.showInputDialog(null, "Digite el nombre del animal:",
                "Nuevo animal", JOptionPane.QUESTION_MESSAGE);

        // Verificar si el nombre es válido
        if (nombreAnimal[0] == null || nombreAnimal[0].trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Retornamos false si los datos no son válidos
        }

        // Pedir la característica distintiva del animal
        caracteristicaAnimal[0] = JOptionPane.showInputDialog(null, "Digite una característica distintiva del animal:",
                "Nueva característica", JOptionPane.QUESTION_MESSAGE);

        // Verificar si la característica es válida
        if (caracteristicaAnimal[0] == null || caracteristicaAnimal[0].trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una característica válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true; // Retornamos true si los datos fueron capturados correctamente
    }


    public void playRecursive(Node root){
        String[] nombreAnimal = new String[1];
        String[] caracteristicaAnimal = new String[1];
        // Mostramos un cuadro de diálogo con botones de opción
        int respuesta = makeQuestion("El animal que esta pensando : ", root.getData().preguntar());

        if (respuesta == JOptionPane.YES_OPTION) {
            if(root.getData().isAnimal()){
                showMessage("¡El animal ya existe!");
                System.out.println("¡El animal ya existe!");
                quieresJugarOtraVez();

            } else{
                playRecursive(root.getRigt()); // si es si y no se trata de un animal
            }

        } else { //SI LA RESPUESTA ES NO
            System.out.println("¡Vamos a seguir buscando!");
            if(root.getLeft()!=null) {
                playRecursive(root.getLeft());
            }else {
                    //si no hay izquierdo y estamos en una caracteristica
                if (!root.getData().isAnimal()) {

                    System.out.println("Estamos debajo de una caracteristica solamente ingresamos");
                    showMessage("¡No hay informacion de este animal vamos a agregarla #1!");
                    if (capturarDatosAnimal(nombreAnimal, caracteristicaAnimal)) {
                        // Mostrar la información capturada
                        showNewAnimalAdd(nombreAnimal[0],caracteristicaAnimal[0]);
                        // Crear un nuevo animal y nodo
                        Animal animal = new Animal(nombreAnimal[0], caracteristicaAnimal[0], true);
                        Node animalNodo = new Node(animal, null, null, root, false);
                        root.setLeft(animalNodo); // Ingresar a la izquierda del nodo actual
                        inorder();
                        play();
                    }
                    //fin de captura de datos

                }//FIN DEL PRIMER CASO

                else {// si soy animal y voy a ingresar abajo mio tengo que crear el nodo de caracteristica para el arbol
                    //Si mi caracteristica no esta agregada entonces vengo de no y debo hacer el rearme #1
                    if (root.getParent().getData().getCaracteristica() != root.getData().getCaracteristica()) {
                        if (!root.getCaracteristicaAdded()){

                            System.out.println("Hay que hacer switch ya que vamos a ingresar debajo de un animal");

                            Animal carac_animal = new Animal("1", root.getData().getCaracteristica(), false);
                            Node carac_nodo = new Node(carac_animal, null, null, root.getParent());

                            root.getParent().setLeft(carac_nodo); //padre de ballena en este momento que era la caract que no cumple ballena //la cambiamos por mi caracteristica
                            carac_nodo.setRigt(root); //como es mi caracteristica me voy a si
                            root.setParent(carac_nodo); //mi padre ahora va ser esa caracteristica mia
                            //decir que la caracteristica de root = por ejemplo ballena esta agregada
                            root.setCaracteristicaAdded(true);

                            showMessage("¡No hay informacion de este animal vamos a agregarla! #2");

                            if (capturarDatosAnimal(nombreAnimal, caracteristicaAnimal)) {
                                // Mostrar la información capturada
                                showNewAnimalAdd(nombreAnimal[0],caracteristicaAnimal[0]);
                                Animal animal = new Animal(nombreAnimal[0], caracteristicaAnimal[0], true);
                                Node nodeAnimal = new Node(animal, null, null, carac_nodo, false);
                                carac_nodo.setLeft(nodeAnimal);//pongo de no al animal nuevo que no le he agregado la caracteristica
                                inorder();
                                play();
                            }

                    }
                        else { //caso caracteristica agregada pero queda en la parte superior

                            showMessage("¡No hay informacion de este animal vamos a agregarla #4!");
                            if (capturarDatosAnimal(nombreAnimal, caracteristicaAnimal)) {
                                // Mostrar la información capturada
                                showNewAnimalAdd(nombreAnimal[0],caracteristicaAnimal[0]);
                                Animal animal = new Animal(nombreAnimal[0], caracteristicaAnimal[0], true);
                                Animal animalCaracteristica = new Animal("",caracteristicaAnimal[0],false);

                                Node parent = root.getParent();
                                Node animalNodeCaracte = new Node(animalCaracteristica,null,null,parent);

                                parent.setLeft(animalNodeCaracte);
                                Node animalObj = new Node(animal,null,null, animalNodeCaracte, true);
                                animalNodeCaracte.setRigt(animalObj);
                                animalNodeCaracte.setLeft(root);
                                root.setParent(animalNodeCaracte);
                                //carac_nodo.setLeft(new Node(animal, null, null, carac_nodo));
                                inorder();
                                play();
                            }

                        }
                   }
                    else { //caso de que mi caracteristica ya esta agregada y es mi padre - esta justo arriba

                        showMessage("¡No hay informacion de este animal vamos a agregarla #3!");

                        if (capturarDatosAnimal(nombreAnimal, caracteristicaAnimal)) {
                            // Mostrar la información capturada
                            showNewAnimalAdd(nombreAnimal[0],caracteristicaAnimal[0]);
                            Animal animal = new Animal(nombreAnimal[0], caracteristicaAnimal[0], true);
                            Animal animalCaracteristica = new Animal("",caracteristicaAnimal[0],false);

                            Node parent = root.getParent();
                            Node animalNodeCaracte = new Node(animalCaracteristica,null,null,parent);
                            parent.setRigt(animalNodeCaracte);
                            Node animalObj = new Node(animal,null,null, animalNodeCaracte, true);
                            animalNodeCaracte.setRigt(animalObj);
                            animalNodeCaracte.setLeft(root);
                            root.setParent(animalNodeCaracte);
                            inorder();
                            play();
                        }

                    }
                }
            }
        }

    }

    @Override
    public void insert(Animal data) {
        if(isEmpty()){
            base = new Node(data, null, null,null);
            return;
        }
        insertRecursive(base, data);
    }

    public void loadTree(){
        Animal data = new Animal("1","ave",false);
        base =  new Node(data, null, null, null);

        Animal data1 = new Animal("","reptil",false);
        Node reptil = new Node(data1,null,null, base);
        base.setLeft(reptil);
        Animal data2 = new Animal("Lagarto","1",true);
        Node lagarto = new Node(data2,null,null, reptil, true);
        reptil.setRigt(lagarto);
        //regresamos a base
        Animal data3 = new Animal("1","cacarea", false);
        Node cacarea = new Node(data3,null,null, base);
        base.setRigt(cacarea);
        Animal data4 = new Animal("aguila","1", true);
        Node aguila = new Node(data4,null,null,cacarea, true);
        cacarea.setLeft(aguila);
        Animal data5 = new Animal("gallina","1",true);
        Node gallina = new Node(data5,null,null,cacarea, true);
        cacarea.setRigt(gallina);
    }

    private void insertRecursive(Node base, Animal data) {
        /*
        if (data < base.getData()) {
            if (base.getLeft() == null) {
                base.setLeft(new Node(data, null, null));
            } else {
                insertRecursive(base.getLeft(), data);
            }
        } else {
            if (base.getRigt() == null) {
                base.setRigt(new Node(data, null, null));
            } else {
                insertRecursive(base.getRigt(), data);
            }
        }
        */
    }

    @Override
    public void inorder() {
        if (base != null) {
            inOrderRecursive(base);
        }
        System.out.println("Recorrido inorder terminado");
    }

    private void inOrderRecursive(Node root) {
        if (root != null) {
            inOrderRecursive(root.getLeft());
            System.out.println(root.getData().preguntar());
            inOrderRecursive(root.getRigt());
        }
    }

    @Override
    public void preorder() {
        if (base != null) {
            preOrderRecursive(base);
        }
        System.out.println("Recorrido preorder terminado");
    }

    private void preOrderRecursive(Node root) {
        if (root != null) {
            System.out.println(root.getData().preguntar());
            preOrderRecursive(root.getLeft());
            preOrderRecursive(root.getRigt());
        }
    }

    @Override
    public void posorder() {
        if (base != null) {
            posOrderRecursive(base);
        }
        System.out.println("Recorrido posorder terminado");
    }

    private void posOrderRecursive(Node root) {
        if (root != null) {
            posOrderRecursive(root.getLeft());
            posOrderRecursive(root.getRigt());
            System.out.println(root.getData().preguntar());
        }
    }


    @Override
    public void printTreeFormat(){
        if (base != null) {
            System.out.println(printTreeFormatRecursive(base));
        }
        System.out.println("Recorrido tree format terminado");
    }

    private String printTreeFormatRecursive(Node root){
        if (root != null) {
            return printTreeFormatRecursive(root.getLeft()) + "   " + root.getData().preguntar() + "   " + printTreeFormatRecursive(root.getRigt());
        }
        return "NULL";
    }

    @Override
    public void poda() {
        if (base != null) {
            podaRecursive(base);
        }
        System.out.println("Poda terminada");
    }

    private void podaRecursive(Node root) {
        if (root != null) {
            if (!root.getLeft().hasChildren()) {
                System.out.println("Se eliminó " + root.getLeft().getData().toString());
                root.setLeft(null);
            } else {
                podaRecursive(root.getLeft());
            }
            if (!root.getRigt().hasChildren()) {
                System.out.println("Se eliminó " + root.getRigt().getData().toString());
                root.setRigt(null);
            } else {
                podaRecursive(root.getRigt());
            }
        }
    }


    @Override
    public void cleanTree() {
        base = null;
    }

    @Override
    public boolean isEmpty() {
        return base == null;
    }
}
