echo "** Creating DBs and users"

mysql -u root -p$MYSQL_ROOT_PASSWORD --execute \
"-- Create databases ---------------------------------------------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS usermanagement;
CREATE DATABASE IF NOT EXISTS categories;
CREATE DATABASE IF NOT EXISTS products;

-- create root user and grant rights
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

-- Create users --------------------------------------------------------------------------------------------------------

-- create separate users for each microservice
CREATE USER 'usermanagementuser'@'%' IDENTIFIED BY '$MYSQL_USERMANAGEMENT_PASSWORD';
CREATE USER 'categoriesuser'@'%' IDENTIFIED BY '$MYSQL_CATEGORIES_PASSWORD';
CREATE USER 'productsuser'@'%' IDENTIFIED BY '$MYSQL_PRODUCTS_PASSWORD';

-- grant necessary privileges to users
GRANT ALL PRIVILEGES ON usermanagement.* TO 'usermanagementuser'@'%';
GRANT ALL PRIVILEGES ON categories.* TO 'categoriesuser'@'%';
GRANT ALL PRIVILEGES ON products.* TO 'productsuser'@'%';"

echo "** Finished creating DBs and users"