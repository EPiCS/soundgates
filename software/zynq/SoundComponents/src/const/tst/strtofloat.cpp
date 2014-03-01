#include <iomanip>
#include <cstdlib>
#include <cstdio>
#include <ctime>
#include <iostream>
/*
int main(int argc, char** argv){

	const std::string foo = "5";
	float myfloat;

	myfloat = std::atof(foo.c_str());
	
	std::cout << "myfloat is: " << myfloat << std::endl;

	return 0;

}*/

/*#include <stdlib.h>
#include <stdio.h> 

int main(int argc, char** argv)  {
  float f;
  char s [] = "1.23";

  f = atof (s);
  printf ("ATOF(%s) = %f\n", s, f);
}

*/

int main(int argc, char** argv){
 
 //std::setprecision(10);
 std::cout.setf( std::ios::fixed, std:: ios::floatfield ); // floatfield set to fixed
 float a = 0.05;
 float b = 0.06;
 int   max = 10000000;
 float c;
 
 c = a + b;
 clock_t begin = clock();
 for(int i = 0; i < max; i++){
  
  c = c * 1.000001;

 } 
 clock_t end = clock();
 double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC * 1000;
 
 std::cout << "std::cout: " << c << std::endl;
 printf("printf: %f\n", c);
 printf("Elapsed time %fus\n", elapsed_secs);
 return 0;
}
