import Data.Char ( isDigit )
-- import System.Directory ( doesFileExist, removeFile )
-- import Control.Monad ( when )
import Data.String (IsString(fromString))

readLines :: FilePath -> IO [String]
readLines =  fmap lines . readFile

-- for testing functions and checking output without filling the terminal
-- writeLines ::  [String] -> FilePath -> IO ()
-- writeLines outLines = flip writeFile outString
--     where
--         outString = unlines outLines

-- for removing the old output file
-- replace :: FilePath -> IO()
-- replace fp = do
--     exists <- doesFileExist fp
    -- when exists $ removeFile fp

extractNumbers :: [String] -> [String]
extractNumbers = map (filter isDigit)

calibreateNumbers :: [String] -> [String]
calibreateNumbers numberLines = [[head s, last s] | s <- numberLines]

sumLines :: [String] -> Int
sumLines = sum . map read

main :: IO()
main = do
    let inFile =  fromString "input.txt"
    -- let outFile = fromString "output.txt"
    -- replace outFile
    inLines <- readLines inFile
    let outLines = calibreateNumbers $ extractNumbers inLines
    -- writeLines outLines outFile
    print $ sumLines outLines

