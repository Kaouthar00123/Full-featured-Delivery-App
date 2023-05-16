const express = require('express');
const router = express.Router();
const restauModel = require('../models/restauService');
const MenuModel = require('../models/menuService');


//-------Restau
router.get('/restaus', async (req, res) => {
  const restauData = await restauModel.getAllRestau( req, res );
});
router.get('/restau/:restauId', async (req, res) => {
  const restauData = await restauModel.getRestauById( req, res );
});
router.post('/restau', async (req, res) => {
  const restauData = await restauModel.postRestau( req, res );
});
router.put('/restau/:restauId', async (req, res) => {
  const restauData = await restauModel.updateRestau( req, res );
});
router.delete('/restau/:restauId', async (req, res) => {
  const restauData = await restauModel.deleteRestau( req, res );
});

//-------Menu
router.get('/menu/:restauId', async (req, res) => {
  const restauMenuData = await MenuModel.getRestauMenu( req, res );
});

//-------Food
router.get('/food/:foodId', async (req, res) => {
  const restauMenuData = await MenuModel.getFoodById( req, res );
});
router.post('/food/:restauId', async (req, res) => {
  const restauData = await MenuModel.postFoodToRestau( req, res );
});
router.put('/food/:foodId', async (req, res) => {
  const restauData = await MenuModel.updateFoodFromRestau( req, res );
});
router.delete('/food/:foodId', async (req, res) => {
  const restauData = await MenuModel.deleteFoodFromRestau( req, res );
});


//-------User


module.exports = router;