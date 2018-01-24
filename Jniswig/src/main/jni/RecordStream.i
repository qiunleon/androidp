/* File : RecordStream.i */

%module RecordStream

%inline %{
extern int stopFlag;
extern int start(const char *input, const char *output);
extern int stop();
%}