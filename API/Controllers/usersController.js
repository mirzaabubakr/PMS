const httpStatusCode = require('http-status-codes');

const Users = require('../Models/UserModel');
const bcrypt = require('bcryptjs');

module.exports = {
  async getAllUsers(req, res) {
    await Users.find({})
      .then(users => {
        return res.status(httpStatusCode.OK).json({ Message: 'All Users', users });
      })
      .catch(err => {
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error', Error: err });
      });
    return res.status()
  },

  async getUserById(req, res) {
    await Users.findOne({
      Email: req.params.id
    })
      .then(user => {
        return res.status(httpStatusCode.OK).json({ Message: 'User by id', user });
      }).catch(err => {
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error' });
      });
  },

  async getUserByUserName(req, res) {
    await Users.findOne({
      UserName: req.params.username
    })
      .then(user => {
        return res.status(httpStatusCode.OK).json({ Message: 'User by Username', user });
      }).catch(err => {
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Internal Server Error' });
      });
  },

  async updateUser(req, res) {

    if (req.body.OldPassword && req.body.NewPassword) {

      const user = await User.findOne({Email: req.params.id});
      if(!user){
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'User Not Exist' });
      }
      const result = await bcrypt.compare(req.body.OldPassword, user.Password);
      if(!result){
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Old Password Incorrect' });
      }

      return bcrypt.hash(req.body.NewPassword, 10, async (err, hash) => {
        if (err) {
          return res.status(httpStatusCode.BAD_REQUEST).json({
            Message: 'Error hashing password'
          });
        }
        const body = {
          Password: hash
        };

        await Users.updateOne({
          Email: req.params.id
        },
          body
        ).then(() => {
          return res.status(httpStatusCode.OK).json({ Message: 'Password Updated Successfully' });
        }).catch(err => {
          return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Updating Password, Information Incorrect' });
        });

      });
    }
    await Users.updateOne({
      Email: req.params.id
    },
      req.body
    ).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'User Updated Successfully' });
    }).catch(err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Updating User' });
    });

  },

  async deleteUser(req, res) {
    await Users.deleteOne({
      Email: req.params.id
    }).then(() => {
      res.status(httpStatusCode.OK).json({ Message: 'User Deleted Successfully' });
    }).catch(err => {
      res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Deleting User' });
    });
  },

}