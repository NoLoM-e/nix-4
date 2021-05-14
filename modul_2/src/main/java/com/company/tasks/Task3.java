package com.company.tasks;



/*
Дан список городов.
Каждый путь между городами имеет цену (целое положительное число).
Задача - найти самый выгодный путь между двумя городами.
Максимально возможная цена пути - 200000.
n [количество городов <= 10000]
NAME [имя города]
p [количество соседей города NAME]
nr cost [nr - индекс соседа NAME (начиная с 1)]
           [cost - стоимость пути]
r [количество путей, которые надо найти <= 100]
NAME1 NAME2 [NAME1 - начало пути, NAME2 - конец пути]
 */
public class Task3 {

    public static int task3(int[][] graph, int i1, int i2){

        final int SIZE = graph.length;

        int[] d = new int[SIZE]; // минимальное расстояние
        int[] v = new int[SIZE]; // посещенные вершины
        int temp, minindex, min;
        int begin_index = i1;


        //Инициализация вершин и расстояний
        for (int i = 0; i<SIZE; i++)
        {
            d[i] = 10000;
            v[i] = 1;
        }

        d[begin_index] = 0;
        // Шаг алгоритма
        do {
            minindex = 10000;
            min = 10000;
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
            if (minindex != 10000)
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
        } while (minindex < 10000);

        return d[i2];
    }
}
