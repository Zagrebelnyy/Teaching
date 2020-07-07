/*За проезд по каждой дороге взымается некоторая пошлина. Найдите
путь из города А в город B с минимальной величиной S + P, где S - сумма длин
дорог пути, а P - сумма пошлин проезжаемых дорог.
*/

#include <iostream>

using namespace std;

class Graph {
private:
    int value;
    int size;
    int** matrixRoad;
    int** matrixCost;
    bool* visited;
    void print() {
        cout << "Матрица смежности для расстояний между вершинами" << endl;
        for (int row = 0; row < this->size; row++)
        {
            for (int col = 0; col < this->size; col++)
            {
                cout << matrixRoad[row][col]<<' ';
            }
            cout << endl;
        }
        cout << "Матрица смежности для пошлины между вершинами" << endl;
        for (int row = 0; row < this->size; row++)
        {
            for (int col = 0; col < this->size; col++)
            {
                cout << matrixCost[row][col] << ' ';
            }
            cout << endl;
        }
    }
    void fill() {
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                if (row != col) {
                    cout << "Расстояние между " << row + 1 << " вершиной и " << col + 1 << endl;
                    cin >> this->matrixRoad[row][col];
                    cout << "Стоимость пошлины" << endl;
                    cin >> this->matrixCost[row][col];
                }
                else {
                    this->matrixRoad[row][col] = 0;
                    this->matrixCost[row][col] = 0;
                }
            }
            cout << endl;
        }
    }
    void search(int node) {
        int nextNode = 0;
        int minValue = 0;
        this->visited[node] = true;
        for (int col = 0; col < this->size; col++)
        {
            if (this->matrixRoad[node][col] != 0 && this->visited[col] == false) {
                if (minValue == 0 && this->matrixRoad[node][col] != 0) {
                    minValue = this->matrixCost[node][col] + this->matrixRoad[node][col];
                }
                if ((this->matrixCost[node][col] + this->matrixRoad[node][col]) <= minValue) {
                    minValue = this->matrixCost[node][col] + this->matrixRoad[node][col];
                    nextNode = col;
                }
            }
        }

        this->value += minValue;
        if (nextNode != this->size - 1 && nextNode != 0) {
            search(nextNode);
        }
    }
public:
    Graph(int size) {
        this->value = 0;
        this->size = size;
        this->matrixCost = new int *[size];
        this->matrixRoad = new int* [size];
        for (int i = 0; i < this->size; i++)
        {
            matrixCost[i] = new int[this->size];
            matrixRoad[i] = new int[this->size];
        }
        this->visited = new bool [this->size];
        for (int i = 0; i < size; i++) {
            visited[i] = false;
        }
        fill();
        print();
        search(0);
        cout <<"Путь из города А в город В "<<this->value << endl;
    }

    ~Graph() {
        for (int i = 0; i < this->size; i++)
        {
            delete[] this->matrixRoad[i];
            delete[] this->matrixCost[i];
        }
        delete[] this->visited;
    }

};
int main() {
    setlocale(LC_ALL, "Russian");
    cout << "Введите число вершин графа"<<endl;
    int n;
    cin >> n;
    Graph grh(n);
    return 0;
}
