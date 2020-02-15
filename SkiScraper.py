import urllib3
from bs4 import BeautifulSoup
import lxml
import html5lib

def scraper(url):
    http = urllib3.PoolManager()
    response = http.request('GET', url)
    soup = BeautifulSoup(response.data, "lxml")
    return soup

#general query to find stuff in text and output the line it is on
#3 different querys because some of the inputs from the mountains are differents
def query(s, lines, text):
    index = text.find(s)
    output = ""
    l = 0
    if index != -1:
        while l < lines:
            if text[index] == '\n':
                l += 1
            if l < lines:   
                output += text[index]
            index += 1
        print(output)

def query2(s, text):
    index = text.find(s)
    index += len(s)
    result = ""
    if index != -1:
        while text[index] == '\n' or text[index] == ' ':
            index += 1
        while text[index] != '\n':
            result += text[index]
            index += 1
    return result

def query3(s, text):
    index = text.find(s)
    index += len(s)
    result = ""
    if index != -1:
        while text[index] == '\n' or text[index] == ' ':
            index += 1
        while text[index] != '\n' and text[index] != ' ':
            result += text[index]
            index += 1
    return result

#Silver Mountain Scraper
def silverMtn(url):
    soup = scraper(url)
    text = soup.get_text()
    # print(text)
    tempList = query2("Since Last Open:", text)
    print("Since Last Open: ", tempList)
    tempList = query2("Last 24 Hours:", text)
    print("Last 24 Hours: ", tempList)
    tempList = query2("Last 48 Hours:", text)
    print("Last 48 Hours:", tempList)
    tempList = query2("Current Temp @ Mountain House:", text)
    tempList2 = query2("Current Temp @ Kellogg Peak:", text)
    print("temperature Mountain House: ", tempList.strip(), " | Kellog Peak: ", tempList2.strip())
    # query("News & Announcements", 2, text)

#Schweitzer Mountain Scraper
def schweitzerMtn(url):
    # text = soup.body.main.div.div.div.div.next_sibling.next_sibling.next_sibling.next_sibling.div.div.div
    soup = scraper(url)
    tempList = soup.find_all("span")
    # i = 0                      #use this to find where the values are. also use this for more stuff
    # for x in tempList:
    #     if(i < 500):
    #         print(i, " ", x)
    #     i += 1
    print("Last 24 hours ", tempList[13].string.strip())
    print("Last 48 hours ", tempList[17].string.strip())
    print("temperature Base: ", tempList[46].string.strip(), " | Summit: ", tempList[49].string.strip())
    tempList = soup.find_all("p")
    # print("forcast: \n", tempList[5].string.strip())

def lookoutMtn(url):
    soup = scraper(url)
    text = soup.get_text()
    # print(text)
    temp = query2("24-HR:", text)
    print("Last 24 hours ", temp)
    temp = query2("48-HR:", text)
    print("Last 48 hours ", temp)
    temp = query2("Base Temp:", text)
    temp2 = query2("Summit Temp:", text)
    print("temperature Base: ", temp, " | Summit: ", temp2)


def fortyNineDegreesNorth(url):
    soup = scraper(url)
    text = soup.get_text()
    temp = query2("24h", text)
    print("Last 24 hours ", temp)
    temp = query2("48h", text)
    print("Last 48 hours ", temp)
    temp = query2("Operations Calendar", text)
    print("temperature ", temp)

def mountSpokane(url):
    soup = scraper(url)
    text = soup.get_text()
    mydivs = soup.findAll("div", {"class": "conditions--inner"})
    # i = 0
    # for x in mydivs:
    #     print(i, " ", x)
    #     i += 1
    # print(mydivs[2].get_text())
    
    text2 = mydivs[2].get_text()
    temp = query3("NEW SNOW IN LAST 24 HOURS:", text2)
    print("Last 24 Hours ", temp, "\"")
    temp = query3("NEW SNOW IN LAST 24 HOURS:", text2)
    print("Last 48 Hours ", temp, "\"")

    text2 = mydivs[1].get_text()
    temp = query3("Lodge Temp:", text2)
    temp2 = query3("Summit Temp:", text2)
    print("temperature Base: ", temp, " | Summit: ", temp2)
    # print(text)


print("\nSilver Mountain")
url = "https://www.silvermt.com/snow-report"
silverMtn(url)

print("\nSchweitzerMtn")
url = "https://www.schweitzer.com/explore/snow-report/"
schweitzerMtn(url)

print("\nLookout Mountain")
url = "https://skilookout.com/snow-report"
lookoutMtn(url)

print("\n49 Degrees North")
url = "https://www.ski49n.com/"
fortyNineDegreesNorth(url)

print("\nMt. Spokane")
url = "https://www.mtspokane.com/mountain-conditions/"
mountSpokane(url)
