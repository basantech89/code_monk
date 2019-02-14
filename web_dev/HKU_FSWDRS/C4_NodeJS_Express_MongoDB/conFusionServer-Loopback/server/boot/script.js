'use strict';
module.exports = function(app) {
  var Customer = app.models.Customer;
  Customer.findOne({username: 'Admin'}, (err, users) => {
    // if users is not null
    if (!users) {
      Customer.create([
        {username: 'Admin', email: 'admin@confusion.net', password: 'password'},
      ], (err, users) => {
        if (err) throw (err);

        var Role = app.models.Role;
        var RoleMapping = app.models.RoleMapping;

        // if database already contains any role mapping, then destroy it
        RoleMapping.destroyAll();
        Role.findOne({name: 'admin'}, (err, role) => {
          // if (err) throw (err);

          if (!role) {
            Role.create({name: 'admin'}, (err, role) => {
              if (err) throw (err);
              // Mapping between the user and the role
              role.principals.create({
                principalType: RoleMapping.USER,
                principalId: users[0].id,
              }, (err, principal) => {
                if (err) throw (err);
              });
            });
          } else {
            role.principals.create({
              principalType: RoleMapping.USER,
              principalId: users[0].id,
            }, (err, principal) => {
              if (err) throw (err);
            });
          }
        });
      });
    }
  });
};
