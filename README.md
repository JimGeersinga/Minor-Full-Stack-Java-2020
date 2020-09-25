# Minor-Full-Stack-Java-2020


###Mutation testing:

Changed this:
```
if(wordCount == null) {		
    wordCount = value.split(" ").length;
    repository.saveWordCount(value, wordCount);	
}
```
To this:
```
if(wordCount == null) {
    wordCount = value.split(" ").length;
    repository.saveWordCount(value, wordCount);
    return  repository.getWordCount(value);
}
```
Because if `repository.saveWordCount` would fail the correct answer would be returned where a null value would be expected
