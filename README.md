# Poker Hand Strength Evaluator

## Prerequisites

1) Run Ubuntu Docker Container

- `docker pull ubuntu` 
- `docker run -it ubuntu`

2) Pull the repository to docker container. The simple way is:
   
- `docker cp /(source_path) (container_id):/(target_path)`

3) Go to the folder with repository in Docker container
4) Run `./prepare.sh`
5) Run `./run.sh < test-cases.txt > solutions.txt`
6) Once bash script is executed visit `solutions.txt` to see the results

## Known limitations and defects

1) I think there is an error in requirements. The section with those 
   input and output: input `texas-holdem 4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d`
   output: `Ac4d=Ad4s 5d6d As9s KhKd` - **This is an error**
   correct output should be `Ac4d=Ad4s KhKd 5d6d As9s`, 
   because `KhKd` is `ThreeOfKind` not a `FullHouse` (as per requirements)
2) I haven't validated duplicates in Cards
3) The function `combinations` implemented for `OmahaHoldem` can produce 
   incorrect combinations (doesn't meet the rule `Playing cards are 
   2 from a hand and 3 from a board)
4) I think, error handling could be implemented in a more functional way
