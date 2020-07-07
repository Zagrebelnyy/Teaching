#include <iostream>
#include <string>
#include <conio.h>
#include <fstream>
#include <windows.h>
#include <map>
using namespace std;
class Dictionary
{
	multimap<string, string> container;
	string path;
public:
	Dictionary(string name);
	void newDictionary();
	void appendValue();
	void deleteValue();
	void searchValue();
	void changeValue();
	void closeDictionary();
	void sort();
private:
	void read();
	void rewrite();

};

Dictionary::Dictionary(string path)
{
	this->path = path;
	read();
}
void Dictionary::read() {
	string value, str,key;
	ifstream dictionary;
	dictionary.open(path);
	if (dictionary.is_open()){
		while (!dictionary.eof()) {
			getline(dictionary, value);
				for (int i = 0; i < value.length(); i++) {
					if(value[i] != '-'){
						str += value[i];
					}
					else if (value[i] == '-') {
						key = str;
						str = "";
					}
				}
				if (key != "" && str != "") {
					container.emplace(key, str);
				}
				key = "";
				str = "";
		}
		if (!container.empty()) {
			for (const auto p : container) {
				cout << p.first << '-' << p.second << endl;
			}
		}
	}
	else {
		cout << "�� ������ ������ �������� �� �������."<< endl;
	}
}
void Dictionary::rewrite() {
	ofstream dictionary;
	dictionary.open(path);
	if (dictionary.is_open()) {
		for (const auto p : container) {
			dictionary << p.first <<'-'<< p.second << endl;
		}
	}
	else
		cout << "���-�� ����� �� ���" << endl;
	dictionary.close();
}
void Dictionary::newDictionary() {
	ofstream dictionary;
	dictionary.open("dictionary.txt");
	if (dictionary.is_open()) {
		cout << "C������ ������� ������!" << endl;
		container.clear();
	}
	else
		cout << "���-�� ����� �� ���" << endl;
	dictionary.close();
}
void Dictionary::appendValue() {
	string key, value;
	cout << "������� ������� ������� ������ ��������: " << endl;
	cout << "���� :";
	cin >> key;
	cout << "�������� :";
	cin >> value;
	key = '[' + key + "]";
	container.emplace(key, value);
	cout << "������� ������� ��������" << endl;
}
void Dictionary::deleteValue() {
	string key;
	cout << "������� ���� ���� ��������, �������� ������ �������: " << endl;
	cin >> key;
	key = "[" + key + "]";
	if (container.erase(key)) {
		cout << "������� ������� �����." << endl;
	}
	else
		cout << "������� � �������� ������ �� ������." << endl;
}
void Dictionary::searchValue(){
	string key;
	cout << "������� ���� ���� ��������, �������� ������ �����: " << endl;
	cout << "����: ";
	cin >> key;
	key = "[" + key + "]";
	auto eqr = container.equal_range(key);
	if (eqr.first != eqr.second) {
		for (auto it = eqr.first; it != eqr.second; ++it) {
			cout << it->first << "-" << it->second << endl;
		}
	}
	else {
		cout << "������ �������� �� �������." << endl;
	}
}
void Dictionary::changeValue() {
	string key, value;
	cout << "������� ���� ���� ��������, ������� ������ �������� � ��� ����� ��������: " << endl;
	cout << "���� :";
	cin >> key;
	key = '[' + key + "]";
	cout << "�������� :";
	cin >> value;
	if (container.erase(key)) {
		container.emplace(key, value);
		cout << "��������� ������� �������." << endl;
	}
	else {
		cout << "������ �������� �� �������!" << endl;
	}
	

}
void Dictionary::closeDictionary() {
	rewrite();
	exit(0);
}
void Dictionary::sort() {
	cout <<"�������, ��������������� � ������� ����������� ������: "<< endl;
	multimap<int, string>keys;
	for (const auto p : container) {
		int num= container.count(p.first);
		keys.emplace(num, p.first);
	}
	for (const auto p : keys) {
		auto it=container.find(p.second);
		cout << it->first << it->second << endl;
	}
}
void terminal() {
	cout << "�������� ������ �������� :" << endl;
	cout << "1 - ������� ������ �������;" << endl;
	cout << "2 - �������� ������� � �������;" << endl;
	cout << "3 - ��������� ������� � �������� ������ �� �������;" << endl;
	cout << "4 - ����� �������� �� �����;" << endl;
	cout << "5 - �������� �������� ��������;" << endl;
	cout << "6 - ����� ������� � ������� ����������� ������;" << endl;
	cout << "0 - ����� �� ���������." << endl;
	Dictionary obj("dictionary.txt");
	while (true) {
		switch (_getch())
		{
		case '1':
			obj.newDictionary();
			break;
		case '2':
			obj.appendValue();
			break;
		case '3':
			obj.deleteValue();
			break;
		case '4':
			obj.searchValue();
			break;
		case '5':
			obj.changeValue();
			break;
		case '6':
			obj.sort();
			break;
		case '0':
			obj.closeDictionary();
			break;
		default:
			cout << "������� ����� �� ������!" << endl;
		}
	}
}

int main()
{
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);
	setlocale(LC_ALL, "Russian");
	terminal();
	return 0;
}
