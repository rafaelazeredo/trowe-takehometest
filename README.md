# Word Finder

Word finder was built to analise text and output results based on specific selected strategies (longest/shortest).


## Assumptions
- Only words with alphabetic letters are considered [A-Za-z].
- Special character and number are be ignored.
- If special characters or numbers are found in the middle of words those will be split in two and considered as two distinguished words.
- Duplicated words are considered as one entry.
- When multiple words meets the strategy criteria they are all returned.
- Null or empty or blank text return an empty Optional
- Instead of having two method one to find the longest and another to find shortest word, strategy pattern was utilised to achieve single responsibility principle, which gives flexibility to create new strategies (i.e. find the word with most frequency in the text).
 
### Prerequisites

``` - Java 11```

## Running the tests

There are unit tests and one component level test. They can be run using:

```mvn clean test```

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management