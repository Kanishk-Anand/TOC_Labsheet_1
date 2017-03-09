
/*
		TOC Labsheet-1

Qu.1 Implement string matching using Finite Automata

References:
Introduction to Algorithms by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein

*/


#include<stdio.h>
#include<string.h>
#define Char_len 128  // Char_len is the size of alphabet (The total no. of possible characters in pattern and string)

 
 void search(char *string, char *pattern); //The time complexity of the search process is O(n).

 void generateTF(char *pat, int TF[][Char_len], int m); //TF is the 2D array that represents a Finite Automata


	int main()


{

  char *string = "SEARCH FOR PATTERN";

  char *pattern = "PATT";


	  search(string, pattern);

		
  return 0;

}

int obtainnxtstate(char *pattern, int m, int state, int x)


{
  /* If the any character is same as the character next to in the pattern then we simply increment the state*/
  

	if (state < m && x == pattern[state])
  		
		return state+1;
 
	  
	int nxt, j; // nxt stores the result which is in the next state & contains the longest prefix which is also suffix
 
 
  // We will initialise from the largest possible value and will stop when we find a prefix which will also be a suffix
 

//The implementation tries all possible prefixes starting from the longest possible that can be a suffix of “pat[0..k-1]x”



	 for (nxt = state; nxt > 0; nxt--)

  {

	  if(pattern[nxt-1] == x)

	  {
		  for(j = 0; j < nxt-1; j++)
          
	  { 
		
		 if (pattern[j] != pattern[state-nxt+1+j])
 
	 break;
}

	  if (j == nxt-1)	
	
	  return nxt;
		
	}

}
 
  return 0;


}

/* Prints every occurrence of the imput pattern in the given string */


void search(char *string, char *pattern)

 {

	int k, state=0;

 	int t = strlen(string); 
 	int p = strlen(pattern);
	
	int TF[p+1][Char_len];  
 
  	generateTF(pattern, TF, p);
 
 
 // Process the string over Finite Automata.
  

  for (k = 0; k < t; k++)
 
 {

  state = TF[state][string[k]];


  if (state == p)

     {

  printf ("\n The Pattern is founded at index number %d\n", k-p+1);
  

     }

  }

}

/* This function's purpose is to the Transition Function table which represents Finite Automata for any given pattern */

//The Time complexity for generateTF() is *O(p^3*Char_len) 


void generateTF(char *pat, int TF[][Char_len], int p)


{

	int state, l;


       	  for (state = 0; state <= p; ++state)

		  for (l = 0; l < Char_len; ++l)

	  TF[state][l] = obtainnxtstate(pat, p, state, l);

}

 
