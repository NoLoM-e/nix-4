package com.company.util;

public class PathFinder {

    public static int findPath(int[][] graph, int i1, int i2){

        final int SIZE = graph.length;

        int[] d = new int[SIZE]; // минимальное расстояние
        int[] v = new int[SIZE]; // посещенные вершины
        int temp, minindex, min;
        int begin_index = i1;


        //Инициализация вершин и расстояний
        for (int i = 0; i<SIZE; i++)
        {
            d[i] = 200000;
            v[i] = 1;
        }

        d[begin_index] = 0;
        // Шаг алгоритма
        do {
            minindex = 200000;
            min = 200000;
            for (int i = 0; i<SIZE; i++)
            { // Если вершину ещё не обошли и вес меньше min
                if ((v[i] == 1) && (d[i]<min))
                { // Переприсваиваем значения
                    min = d[i];
                    minindex = i;
                }
            }
            // Добавляем найденный минимальный вес
            // к текущему весу вершины
            // и сравниваем с текущим минимальным весом вершины
            if (minindex != 200000)
            {
                for (int i = 0; i<SIZE; i++)
                {
                    if (graph[minindex][i] > 0)
                    {
                        temp = min + graph[minindex][i];
                        if (temp < d[i])
                        {
                            d[i] = temp;
                        }
                    }
                }
                v[minindex] = 0;
            }
        } while (minindex < 200000);

        return d[i2];
    }
}
