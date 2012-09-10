//
//  file.h
//  Lab 1
//
//  Created by Lucas Wiener on 9/10/12.
//  Copyright (c) 2012 skola. All rights reserved.
//

#ifndef Lab_1_file_h
#define Lab_1_file_h

typedef struct _wordpair {
    char *text;
    int index;
} wordpair;

FILE* openfile(const char *filename, const char *mode);
int closefile(FILE *file);

char* readline();
wordpair readPairByLine(int line);

#endif
