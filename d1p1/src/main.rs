use anyhow::anyhow;
use std::fs::File;
use std::io::{BufRead, BufReader, Read};

fn main() -> Result<(), anyhow::Error> {
    let mut f = File::open("./input.txt")?;
    let diff = find_diff(&mut f)?;
    println!("Diff: {}", diff);
    Ok(())
}

/// Find the similarity score between the two lists as read from the input.
/// 
/// Using an ad-hoc approach, collect the first and second elements of each line into two separate
/// vectors. Sort the vectors and then zip them together to calculate the absolute difference
/// between each pair of elements. Sum the differences to get the final score.
/// 
fn find_diff(reader: &mut dyn Read) -> Result<isize, anyhow::Error> {
    let reader = BufReader::new(reader);

    let mut firsts = Vec::new();
    let mut seconds = Vec::new();
    for result in reader.lines() {
        let line = result?;
        let parts = line.split(" ").collect::<Vec<&str>>();

        let a = parts.first()
            .ok_or(anyhow!("No first element"))?
            .parse::<isize>()?;
        let b = parts.last()
            .ok_or(anyhow!("No second element"))?
            .parse::<isize>()?;

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

#[cfg(test)]
mod tests {
    use crate::find_diff;
    
    #[test]
    fn test_find_diff_using_example() {
        let input = "3   4\n\
                           4   3\n\
                           2   5\n\
                           1   3\n\
                           3   9\n\
                           3   3";
        let diff = find_diff(&mut input.as_bytes()).unwrap();
        assert_eq!(diff, 11);
    }

    #[test]
    fn test_find_diff_using_custom_input() {
        let input = "3   4\n\
                           4   3\n\
                           2   5\n\
                           1   3\n\
                           3   9\n\
                           21  2\n\
                           3   3";
        let diff = find_diff(&mut input.as_bytes()).unwrap();
        assert_eq!(diff, 16);
    }
}
