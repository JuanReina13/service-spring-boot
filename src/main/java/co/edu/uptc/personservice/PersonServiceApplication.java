package co.edu.uptc.personservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PersonServiceApplication.class, args);
    }
}

// donde queda nombre del nodo en VM 
// comando cat /etc.hosts

// Cambiar nombre de maquina
// hostnamectl set-hostname <nuevo-nombre>

// nombre es interno de maquina, se agrega normalmente dependiendo la labor que desempeñara

// en linux todo se monta, se realizan 

// solo hay dos tipos de archivos:
// archivos de texto
// archivos binarios

/*
cada que se crea un usuario, este pertenecera a un grupo

ambientes linux: shell, 

root no tiene contraseña

tener un unico archivo para varios servidores defirentes de diferentes maquinas virtuales y maquinas

Tareas:
1 servidores minimo que lleguen al mismo archivo
Deberia ser capaz de tener mil servidores que sean capaces de mantener 50 millones de personas
Ip A *(balanceador de carga) conexta a Ip8 y Ip 9 (mismo jar) que conectan a un servidor con el arvhivo
unificar cada archivo en un servidor network file system NFS, usar un servidor destinado a archivos -> aca debe ir una base de datos 

La idea es que no importa quien guarde, al ser archivos si dos usuarios le dan exactamente al mismo tiempo se puede guardar

Son sistemas de arvhivos remotos

Otra opcion es montar un servicio web que guarde un json con arquitectura 64 bits. En este caso ya no se deberia usar NFS

objetivo es que se soporte la carga de trabajo, que si se daña uno no pasen errores, es el objetivo de esta arquitectura que se ira avanzando a lo largo del avance de la asignatura

*/

/*
- trabajo montar nfs en otro servidor que funcione, aplicacion no se debe tocar, es posible que NFS pida una carpeta, porque NFS me pedira una carpeta (es un protocolo)
- Nginx (mas usada) otras como Apache (menos popular ahora mismo). Se descarga Nginx entrar linux y archivos para montar el balanceador de carga.
min 4 maquinas.
- agregar otro atributo a dto para que aparezca ip de maquina que respondio. 
(todo esto es tolerancia a fallas, minimizando las fallas no eliminando)
*/