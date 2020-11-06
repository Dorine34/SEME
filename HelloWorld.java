/*
 * Copyright (C) 2017 COSLING S.A.S.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

/**
 * Simple Hello World in Choco Solver
 * @author Jean-Guillaume FAGES (cosling)
 * @version choco-solver-4.0.4
 */
/*
import org.chocosolver.solver.Model;*/
import org.chocosolver.solver.variables.IntVar;

import org.chocosolver.solver.variables.RealVar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.IntVar;


/**
 * Use of the choco-solver to generate scenarios described in the report and the slides associated with this code.
 * based of the OpenSource tutorial of Jean-Guillaume FAGES (cosling)
 * @author Vaĺerie Garcin, Nicoletta Prencipe, Suzanne Schlich, Dorine Tabary
 * @version choco-solver-4.0.4
 */


public class HelloWorld {
    public static void main(String[] args) {
        double ALPHA=0.35;
        double t=0.3;
        double max=10000;

        // Create the model
        Model model = new Model();

        // partitions a set "universe" into three disjoint sets x, y and z
        // declare a constant set variable (LB = UB = {1, 2, 3, 5, 7, 8})
        SetVar universe = model.setVar("universe",
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26,27,28,29,30,
                31,32,33,34,35,36,37,38,39,40,
                41,42,43,44,45
        );

        // create a set variable x
        SetVar x = model.setVar("x", new int[]{}, new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26,27,28,29,30,
                31,32,33,34,35,36,37,38,39,40,
                41,42,43,44,45
        });
        SetVar z = model.setVar("z", new int[]{}, new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26,27,28,29,30,
                31,32,33,34,35,36,37,38,39,40,
                41,42,43,44,45
        });

        // partition constraint : union(x,y,z) = universe and all_disjoint(x,y,z)
        model.partition(new SetVar[]{x,  z}, universe).post();


        double[] COEFFSCRI = new double[]{
                0.5,0.5,0.5,1,1,0.5,0.5,1,1,1,
                1,0.5,0.5,0.5,0.5,0.5,1,1,0.5,1,
                0.5,0.5,1,0.5,1,1,1,1,1,1,
                1,1,1,1,0.5,0.5,1,1,0.5,0.5,
                1,1,0.5,1,0.5
        };
        double[] COEFFSPRO = new double[]{
                0.7,0.7,0.7, 0.3,0.1,0.3,0.7,0.3,0.7,0.3,
                0.3,0.3,0.3,0.3,0.3,0.3,0.3,0.3,0.3,0.3,
                0.3,0.3,0.3,0.3,0.7,0.3,0.3,0.9,0.3,0.9,
                0.7,0.3,0.3,0.3,0.3,0.3,0.7,0.7,0.3,0.3,
                0.3,0.3,0.3,0.3,0.3
        };

        String[] NAMES = new String[]{
                "Cross Road",
                "Inclination Break",
                "Impaired Driver",
                "Fog",
                "Big Obstacle",
                "Small Obstacle",
                "Roundabout",
                "Low Contrast",
                "VRU Obstacle",
                "2 Wheels",
                "Traffic Accident",
                "Wind",
                "Hail / Snow / Rain",
                "Bridge",
                "Degraded Equipment",
                "Degrated Vehicle Rolling",
                "Traffic Light Not Working",
                "Road Marking Missing",
                "Pothole",
                "Traffic Sign Unreadable",
                "Curved Road",
                "Speed Bump",
                "Teleoperation Hacking",
                "CEM",
                "Cut Out",
                "Equipments Supervision Hacking",
                "Impaired Walking",
                "Overtaking",
                "Against Rally Driving",
                "Safety Distance Violation",
                "Direction Change Not Communicated",
                "Communication Loss",
                "Right Overtaking",
                "AV Failure",
                "Vehicle Hacking",
                "Sensor Failure",
                "Out Of Zebra Crossing",
                "Cut In",
                "Emergency Vehicles",
                "Late Obstacle Detection",
                "Low Grip",
                "Smart Equipment Hacking",
                "Overspeed Human Driving",
                "Faulty Sensor",
                "Glare"
        };


        // find one partition
        double prob=1;
        double probCri=1;
        double probT=1;
        int num=0;
        //bool use = false;

        try {

            String content = "Ceci est le contenu ajouté au fichier";

            File file = new File("test.txt");

            // créer le fichier s'il n'existe pas
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            double probSimulation=1;
            while((model.getSolver().solve())&&(num<max)){
                if (
                    //les exclusions
                        !(z.getValue().isEmpty()) &&
                                !(z.getValue().contains(14) && z.getValue().contains(7))&&
                                !(z.getValue().contains(1) && z.getValue().contains(7))&&
                                !(z.getValue().contains(1) && z.getValue().contains(14))&&
                                !(z.getValue().contains(5) && z.getValue().contains(6))&&
                                !(z.getValue().contains(4) && z.getValue().contains(13))&&

                                !(z.getValue().contains(5) && !(z.getValue().contains(11)))&&//5->11
                                !(z.getValue().contains(40) && !(z.getValue().contains(34)))&&//40->34
                                !(z.getValue().contains(41) && !(z.getValue().contains(13)))&&//41->13
                                !(z.getValue().contains(8) && !(z.getValue().contains(34)))&&//34
                                !(z.getValue().contains(8) && !(z.getValue().contains(35)))&&//35
                                !(z.getValue().contains(36) && !(z.getValue().contains(34)))&&//36->34
                                !(z.getValue().contains(5) && !(z.getValue().contains(19)))&&//5->19
                                !(z.getValue().contains(5) && !(z.getValue().contains(22)))&&//5->22
                                !(z.getValue().contains(15) && !(z.getValue().contains(18)))&&//15->18
                                !(z.getValue().contains(15) && !(z.getValue().contains(19)))&&//15->19
                                !(z.getValue().contains(15) && !(z.getValue().contains(20)))&&//15->20
                                !(z.getValue().contains(21) && !(z.getValue().contains(7)))&&//21->7
                                !(z.getValue().contains(28) && !(z.getValue().contains(10)))&&//210
                                !(z.getValue().contains(30) && !(z.getValue().contains(22)))&&//30->22
                                !(z.getValue().contains(30) && !(z.getValue().contains(40)))&&//30->40
                                !(z.getValue().contains(30) && !(z.getValue().contains(8)))&&//30->8
                                !(z.getValue().contains(32) && !(z.getValue().contains(17)))//32->17

                )
                {
                    probSimulation=1;
                    probCri=1;
                    probT=1;
                    for (int i=1; i<COEFFSCRI.length+1 ; i++)
                    {
                        if (z.getValue().contains(i)) {
                            probCri=probCri+COEFFSCRI[i-1];
                        }
                    }
                    probCri=probCri/45;
                    //  System.out.println("hello cri =" + probCri);

                    //prob =  probSimulation;
                    prob =  1;
                    for (int i=1; i<COEFFSPRO.length+1 ; i++) {
                        if (z.getValue().contains(i)) {
                            //prob = prob*COEFFSPRO[i-1]/(1-COEFFSPRO[i-1]);
                            prob = prob*COEFFSPRO[i-1];
                        }
                    }
                    //    System.out.println("hello prob =" + prob);

                    //System.out.println("PROBvoid " + PROBvoid);
                    //PROBvoid =1;
                    probT=t*probCri+(1-t)*prob;

                    //      System.out.println("hello probT =" + probT);

                    if (probT>ALPHA) {
                        //System.out.println("Partition found!");
                        num++;
                        bw.write("\n"+"scenario " + num + " proposé :" );
                        for (int i=1; i<46 ; i++)
                        {
                            if (z.getValue().contains(i)) {
                                //System.out.print( NAMES[i-1] +"("+i+"), " );
                                bw.write(NAMES[i-1] +"("+i+"), ");
                            }
                            //PROBvoid=PROBvoid *(1-COEFFSPRO[i-1]);
                            //System.out.println("PROBvoid " + PROBvoid);
                        }
                        System.out.println("scenario " + num + " proposé " + z.getValue());
                    }


                    prob=1;
                }

            }
            bw.close();

            System.out.println("Modification terminée!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("PROBvoid " + PROBvoid);

    }

}
