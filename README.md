# Minor-Full-Stack-Java-2020


### Mutation testing:

Changed this:
```
if(wordCount == null) {		
    wordCount = value.split(" ").length;
    repository.saveWordCount(value, wordCount);	
}
```
Added verification if the mock call has been called
Because if `repository.saveWordCount` would fail the correct answer would be returned whereas a null value would be expected
