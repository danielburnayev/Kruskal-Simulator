# Kruskal-Simulator

A GUI that shows you the step-by-step process Kruskal's Algorithm takes when determining the minimum spanning tree of a customizable graph that can be changed, with the bullet points below being my contributions to the project:
* Wrote the majority of the Graph310 class
* Included methods for binary search trees such as removeMax(), findMax(), and values() in the WeissBST class
* Wrote methods for representing the keys and values of a map with keySet() and values() in the Map310 class
* Implemented Kruskal's Algorithm through methods doNextStep() and checkCyclicalness() in the Kruskal310 class

## Simulation/Transforming Mode
<img src='Kruskal Algorithm Demo Gif Simulation Mode.gif' title='Simulation Mode' width='' alt='Simulation Mode' />

## Picking Mode
<img src='Kruskal Algorithm Demo Gif Picking Mode.gif' title='Picking Mode' width='' alt='Picking Mode' />

## Editing Mode
<img src='Kruskal Algorithm Demo Gif Editing Mode.gif' title='Editing Mode' width='' alt='Editing Mode' />

GIFs were created with [LICEcap](https://www.cockos.com/licecap/) for MacOS

## How to run the simulator:
1. Download the [src.zip file](src.zip) and open it
2. Open your terminal and direct it to the newly created src directory
3. Compile all the Java files with the following command: <strong>javac -cp 310libs.jar *.java</strong> (shouldn't be necessary; only do this when not all the class files aren't there)
4. Run the simulator with following command: <strong>java -cp .:310libs.jar SimGUI</strong> (do <strong>java -cp .;310libs.jar SimGUI</strong> if you are on Windows)



