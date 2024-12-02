use std::collections::HashMap;
use anyhow::anyhow;
use std::fs::File;
use std::io::{BufRead, BufReader, Cursor, Read, Seek};

fn main() -> Result<(), anyhow::Error> {
    let mut f = File::open("./input.txt")?;

    let diff = similarity_score_by_diff(&mut f)?;
    f.rewind()?;
    let counts = similarity_score_by_counts(&mut f)?;

    println!("Similarity score by diff: {}", diff);
    println!("Similarity score by counts: {}", counts);

    Ok(())
}

/// Find the similarity score between the two lists as read from the input.
/// 
/// Using an ad-hoc approach, collect the first and second elements of each line into two separate
/// vectors. Sort the vectors and then zip them together to calculate the absolute difference
/// between each pair of elements. Sum the differences to get the final score.
/// 
fn similarity_score_by_diff(reader: &mut dyn Read) -> Result<isize, anyhow::Error> {
    let mut firsts = Vec::new();
    let mut seconds = Vec::new();
    for result in entries_from_reader(reader) {
        let (a, b) = result?;
        firsts.push(a);
        seconds.push(b);
    }

    firsts.sort();
    seconds.sort();

    let diff = firsts.into_iter()
        .zip(seconds.into_iter())
        .map(|(a, b)| (a - b).abs())
        .sum::<isize>();

    Ok(diff)
}

fn similarity_score_by_counts(mut reader: &mut dyn Read) -> Result<isize, anyhow::Error> {
    let mut lefts = Vec::new();
    let mut right_counts = HashMap::new();
    for result in entries_from_reader(&mut reader) {
        let (a, b) = result?;
        right_counts.entry(b).and_modify(|count| *count += 1).or_insert(1);
        lefts.push(a);
    }

    let mut score = 0;
    for left in lefts {
        if let Some(count) = right_counts.get(&left) {
            score += left * count;
        }
    }
    Ok(score)
}

fn entries_from_reader(reader: impl Read) -> impl Iterator<Item = Result<(isize, isize), anyhow::Error>> {
    BufReader::new(reader)
        .lines()
        .map(|result| {
            let line = result?;
            let parts = line.split(" ").collect::<Vec<&str>>();

            let a = parts.first()
                .ok_or(anyhow!("No first element"))?
                .parse::<isize>()?;
            let b = parts.last()
                .ok_or(anyhow!("No second element"))?
                .parse::<isize>()?;

            Ok((a, b))
        })
}

#[cfg(test)]
mod tests {
    use crate::{similarity_score_by_counts, similarity_score_by_diff};
    
    #[test]
    fn test_similarity_score_by_diff_using_example() {
        let input = "3   4\n\
                           4   3\n\
                           2   5\n\
                           1   3\n\
                           3   9\n\
                           3   3";
        let diff = similarity_score_by_diff(&mut input.as_bytes()).unwrap();
        assert_eq!(diff, 11);
    }

    #[test]
    fn test_similarity_score_by_diff_using_custom_input() {
        let input = "3   4\n\
                           4   3\n\
                           2   5\n\
                           1   3\n\
                           3   9\n\
                           21  2\n\
                           3   3";
        let diff = similarity_score_by_diff(&mut input.as_bytes()).unwrap();
        assert_eq!(diff, 16);
    }

    #[test]
    fn test_similarity_score_by_counts_using_example() {
        let input = "3   4\n\
                           4   3\n\
                           2   5\n\
                           1   3\n\
                           3   9\n\
                           3   3";
        let count = similarity_score_by_counts(&mut input.as_bytes()).unwrap();
        assert_eq!(count, 31);
    }
}
