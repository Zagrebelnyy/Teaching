#include <iostream>

using namespace std;

class BinaryTree
{
private:
    class Node
    {
    private:
        int value;
        Node* lChild;
        Node* rChild;
    public:
        Node()
        {
            this->lChild = nullptr;
            this->rChild = nullptr;
        }
        Node(int value) 
        {
            this->value = value;
            this->lChild = nullptr;
            this->rChild = nullptr;
        }

        int getValue()
        {
            return this->value;
        }

        Node* getLChild()
        {
            return this->lChild;
        }

        Node* getRChild()
        {
            return this->rChild;
        }

        void setLChild(Node* node)
        {
            this->lChild = node;
        }

        void setRChild(Node* node)
        {
            this->rChild = node;
        }
    };
    int count = 0;
    Node* head;
    int size = 0;
public:
    BinaryTree(int value)
    {
        Node* node = new Node (value);
        this->head = node;
        this->size++;
    }

    void add(int elem, Node *node)
    {
        if (elem < node->getValue())
        {
            if (node->getLChild() != nullptr)
            {
                add(elem, node->getLChild());
            }
            else
            {
              this->size++;
              Node *newNode = new Node(elem);
              node->setLChild(newNode);
            }
        }
        if(elem > node->getValue())
        {
            if (node->getRChild() != nullptr)
            {
                add(elem, node->getRChild());
            }
            else
            {
                this->size++;
                Node* newNode = new Node(elem);
                node->setRChild(newNode);
            }

        }
        if(elem == node->getValue()) 
        {
            cout << "Такой узел уже существует!" << endl;
        }
    }
    void add(int elem)
    {
        add(elem, head);
    }
  
    void searchNode(Node* node, int nodeValue)
    {
        count++;
        if (node->getLChild() != nullptr && node->getRChild() != nullptr && node->getLChild()->getLChild() != nullptr)
        {
            if ((node->getLChild()->getLChild()->getLChild() == nullptr) && (node->getRChild()->getRChild() == nullptr) && (node->getRChild()->getLChild() == nullptr))
            {
                nodeValue = node->getValue();
                cout <<"Номер вершины "<< nodeValue << endl;
            }
        }
        if (count == size && nodeValue == -1) {
            cout << "Вершин не найдено" << endl;
        }
        if (node->getLChild() != nullptr) {
            searchNode(node->getLChild(), nodeValue);
        }
        if (node->getRChild() != nullptr) {
            searchNode(node->getRChild(), nodeValue);
        }
    }
          
    void searchNode()
    {
        int countR = 0;
        int countL = 0;
        this->count = 0;
        int nodeValue = -1;
        searchNode(head, nodeValue);
    }
};
void mainMenu(BinaryTree tr)
{
    while (true) {
        cout << "Выберите нужный пункт меню" << endl;
        cout << "1. Добавить элемент в бинарное дерево" << endl;
        cout << "2. Найти номера вершин, у которых количество потомков в левом\n"
            "поддереве отличается от количества потомков в правом поддереве на 1." << endl;
        cout << "3. Выход" << endl;
        int control;
        cin >> control;
        switch (control)
        {
        case 1:
            cout << "Введите значение новой вершины дерева: " << endl;
            int n;
            cin >> n;
            tr.add(n);
            break;
        case 2:
            tr.searchNode();
            break;
        case 3:
            exit(1);
            break;
        default:
            cout << "Выберите один из перечисленных пунктов меню" << endl;
            mainMenu(tr);
            break;
        }
    }
}
int main()
{
    setlocale(LC_ALL, "Russian");
    cout << "Введите значение корневого элемента бинарного дерева" << endl;
    int value;
    cin >> value;
    BinaryTree tr(value);
    mainMenu(tr);
    return 0;
}
