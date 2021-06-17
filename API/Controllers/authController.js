const Joi = require('joi'); //Javascript object validator
const httpStatusCode = require('http-status-codes'); // Http StatusCodes
const bcrypt = require('bcryptjs'); // Encrypt Password
const jwt = require('jsonwebtoken'); // Authenticate User and allow routes

const User = require('../Models/UserModel');
const helper = require('../Helpers/helper');
const dbConf = require('../Config/secret'); // Application Secret

module.exports ={
  async CreateUser(req, res){
    console.log(req.body);
    const schema = Joi.object().keys({
      Name: Joi.string().required(),
      Email: Joi.string().required(),
      PhoneNumber: Joi.string().required(),
      Address: Joi.string().required(),
      Password: Joi.string().required()
    });
    const { error, value } = Joi.validate(req.body, schema); //returns error if not validates else returns req.body object
    if(error && error.details){
      return res.status(httpStatusCode.BAD_REQUEST).json({
        msg: error.details
      });
    }

    const UserEmail = await User.findOne({ Email: helper.toLower( req.body.Email) });
    if(UserEmail){
      return res.status(httpStatusCode.CONFLICT).json({ 
        Message: 'Email already exists, Please Login'
      });
    }
    
    return bcrypt.hash( value.Password, 10, (err, hash) => {
      if(err){
        return res.status(httpStatusCode.BAD_REQUEST).json({
          Message: 'Error hashing password'
        });
      }

      const body = {
        Name: value.Name,
        Email: value.Email,
        PhoneNumber: value.PhoneNumber,
        Address: value.Address,
        Password: hash
      };

      User.create(body).then((user)=>{
        _user = {
          _id: user._id,
          Name: user.Name,
          Email: user.Email,
          PhoneNumber: user.PhoneNumber,
          Address: user.Address,
        }
        const token = jwt.sign({data: _user}, dbConf.secret, {
          expiresIn: "24h"
        });
        
        //res.cookie('auth', token);
        return res.status(httpStatusCode.CREATED).json(
          {
            Message: 'User Created Successfully',
            Data: user,
            Token: token
          });
      }).catch(err =>  {
        return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({
          Message: 'User can not be created',
          Error: err.details
        });
      });
    });
  },

  async LoginUser(req, res){
    if(!req.body.Email || !req.body.Password){
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'No Empty fields are allowed'});
    }

    await User.findOne({Email: helper.toLower(req.body.Email)}).then(user=>{
      if(!user){
        return res.status(httpStatusCode.NOT_FOUND).json({ Message: 'User does not exist'});
      }

      return bcrypt.compare(req.body.Password, user.Password).then(result=>{
        if(!result){
          return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Password is incorrect'});
        }
        
        _user = {
          _id: user._id,
          Name: user.Name,
          Email: user.Email,
          PhoneNumber: user.PhoneNumber,
          Address: user.Address,
        }

        const token = jwt.sign({data: _user}, dbConf.secret,{
          expiresIn: '24h'
        });
        res.cookie('auth', token);
        return res.status(httpStatusCode.OK).json({Message: 'Login Successful', user, token});
      });
    }).catch(err=>{
      return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({ Message: 'Error Occured'});
    });
  }
}