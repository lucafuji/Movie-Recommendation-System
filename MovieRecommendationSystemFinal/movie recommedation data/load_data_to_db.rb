require "mysql"
require "date"
dbh = Mysql.new("localhost", "root", "12345qwert", "MovieRecommendationSystem")



def create_user_info(dbh)
   dbh.query("drop table  if exists userinfo")
   dbh.query("""create table userinfo (
    userid int(50) not null  primary key, 
     age int(8),
     gender varchar(2),
      occupation varchar(20),
       zipcode varchar(10) 
   )""")
end

def insert_user_info(dbh,filename)
  File.open(filename).each_line do |line|
    attrs = line.split("|")
    dbh.query("""insert into userinfo values(
      #{attrs[0]},#{attrs[1]},\'#{attrs[2]}\',\'#{attrs[3]}\',\'#{attrs[4]}\'
    )""")
  end
end

def create_item_info(dbh)
   dbh.query("drop table  if exists iteminfo")
   dbh.query("""create table iteminfo(
    movieid int(50) not null primary key,
    movietitle varchar(20),
    releasedate date,
    videoreleasedate date,
    IMDbURL varchar(200),
     unknown int(1),
      Action int(1),
       Adventure int(1),
        Animation int(1),
            Children_s int(1),
             Comedy int(1),
              Crime int(1),
               Documentary int(1),
                Drama int(1),
                 Fantasy int(1),
            Film_Noir   int(1),
             Horror int(1),
              Musical int(1),
               Mystery int(1),
                Romance int(1),
                 Sci_Fi int(1),
                 
            Thriller      int(1),
             War int(1),
              Western int(1)
   );""")
end

def insert_item_info(dbh,filename)
  dbh.query("delete from  iteminfo")
  File.open(filename).each_line do |line|
    attrs = line.split("|")
    attrs[1..1] = [attrs[1][0,attrs[1].length-6],attrs[1][-5..attrs[1].length-2]]
    attrs.delete_at(4)
    attrs = attrs.collect {|x| 
      puts x
      if x.class.name.eql? "String"
        if x[-1] == "|"
          x[-1] =""
        end
       
        y = x
        begin 
          y = Date.strptime(x,"%d-%b-%Y").strftime("%Y-%m-%d")
        rescue
          y = x 
        end
        y = "\""+y.gsub("\"","\\\"").gsub("\'","\\\'")+"\"" 
        
      else
        x
      end
      }
    dbh.query("""insert into iteminfo values(
      #{attrs.join(",")}
    )""")
  end
end

def create_table(dbh,table_name,attrs)
  dbh.query("drop table if exists #{table_name}")
  query_string = "create table #{table_name}("
  attrs.each do|attr,modifier|
    query_string+=attr
    modifier.each do |x|
      query_string+=" "+x
    end
    query_string+=","
  end
  query_string[-1]=");"
  dbh.query(query_string)
end

def insert_table(dbh,filename,table_name,attrlists="",delimiter="|")
  dbh.query("delete from  #{table_name}")
  File.open(filename).each_line do |line|
    attrs = line.split(delimiter) 
    attrs.delete_at(-1)
    attrs = attrs.collect {|x| 
      if x.class.name.eql? "String"
      "\""+x.gsub("\"","\\\"").gsub("\'","\\\'")+"\"" 
      else
        x
      end
      }
    if attrlists.length>0
      dbh.query("""insert into #{table_name}(#{attrlists}) values(
        #{attrs.join(",")} 
      )""") if attrs.length >0
    else
      dbh.query("""insert into #{table_name} values(
        #{attrs.join(",")} 
      )""") if attrs.length >0
    end
    
    
  end
end
#create_user_info(dbh)
#insert_user_info(dbh,"ml-100k/u.user")
#create_item_info(dbh)
#insert_item_info(dbh,"ml-100k/u.item")

#create_table(dbh,"genre",{
 # "id"=>"int(8) primary key not null ",
 # "name"=>"varchar(20)"
#})
#insert_table(dbh,"ml-100k/u.genre","genre")

#create_table(dbh,"occupation",{
# "name"=>"varchar(20) primary key not null ",
#})
#insert_table(dbh,"ml-100k/u.occupation","occupation")

#create_table(dbh,"info",{
 # "value"=>"int(32)",
 #{}"name"=>"varchar(20) primary key not null "
#})
#insert_table(dbh,"ml-100k/u.info","info","value,name"," ")

#puts Date.strptime(x,"01-%b-%Y").strftime("%Y-%m-%d")
insert_table(dbh,"ml-100k/u.data","rating",""," ")