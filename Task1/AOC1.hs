import Data.Char
import System.Directory ( doesFileExist, removeFile )
import Control.Monad ( when )
import Data.String
import Data.List

readLines :: FilePath -> IO [String]
readLines =  fmap lines . readFile

-- for testing functions and checking output without filling the terminal
writeLines ::  [String] -> FilePath -> IO ()
writeLines outLines = flip writeFile outString
    where
        outString = unlines outLines

-- for removing the old output file
replace :: FilePath -> IO()
replace fp = do
    exists <- doesFileExist fp
    when exists $ removeFile fp

extractNumbers :: [String] -> [String]
extractNumbers = map (filter isDigit)

calibreateNumbers :: [String] -> [String]
calibreateNumbers numberLines = [[head s, last s] | s <- numberLines]

sumLines :: [String] -> Int
sumLines = sum . map read

inFile :: FilePath
inFile = "input.txt"

outFile :: FilePath
outFile = "output.txt"

main1 :: IO ()
main1 = do
    replace outFile
    inLines <- readLines inFile
    let outLines = calibreateNumbers $ extractNumbers inLines
    writeLines outLines outFile
    print $ sumLines outLines

---- part 2 --------------------------------------------------------------------

digitStrings :: [String]
digitStrings = ["zero", "one", "two", "three", "four"
                , "five", "six", "seven", "eight","nine"]

stringMatches :: String -> [Bool]
stringMatches s = map (`isPrefixOf` s) digitStrings

getMatchingString :: [Bool] -> [String] -> String
getMatchingString (b:ools) (s:tr)
    | b         = s
    | otherwise = getMatchingString ools tr

firstDigit :: String -> String
firstDigit str@(s:tr)
    | isDigit s              = [s]
    | or $ stringMatches str = getMatchingString (stringMatches str) (map show [0..9])
    | otherwise              = firstDigit tr


stringMatchesRev :: String -> [Bool]
stringMatchesRev s = map ((`isPrefixOf` s) . reverse) digitStrings

lastDigit :: String -> String
lastDigit str@(s:tr)
    | isDigit s              = [s]
    | or $ stringMatchesRev str = getMatchingString (stringMatchesRev str) (map show [0..9])
    | otherwise              = lastDigit tr

calibrate :: [String] -> [String]
calibrate str = [firstDigit s ++ lastDigit (reverse s) | s <- str]

main2 :: IO ()
main2 = do
    replace outFile
    inLines <- readLines inFile
    let outLines = calibrate inLines
    writeLines outLines outFile
    print $ sumLines outLines
