/**
 * The command adds a new promotion to the promotion list.
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;

public class AddPromotionCommand extends Command {
    private final Promotion newPromotion;
    public Food promotionFood;
    public static final String MESSAGE_SUCCESS = "New promotion added: \n"
            + "%1$s\n"
            + "Now you have %2$s promotional items in the list.\n";

    /**
     * Default constructor.
     * @param promotion the promotion to be added.
     */
    public AddPromotionCommand(Promotion promotion) {
        this.newPromotion = promotion;
    }

    /**
     * Acquires the food for which the promotion has to be implemented.
     * @param list The food list.
     * @throws CommandException when the command requirements are not fulfilled.
     */
    public void obtainPromotionFood(FoodList list) throws CommandException {
        CommandUtil.requireValidName(list, newPromotion.getName());
        promotionFood = list.get(newPromotion.getName());
    }

    /**
     * Adds promotion to promotionList and store it if the promotion does not already exist,
     * otherwise throws Command exception.
     *
     * @param model storage model.
     * @param storage The storage we have.
     * @return Message feedback to user.
     * @throws CommandException when the command requirements are not fulfilled.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        PromotionList promotionList = model.getPromotionList();
        obtainPromotionFood(list);
        CommandUtil.requireNotOverlappingTime(promotionList, newPromotion);
        CommandUtil.requireValidPromotionDates(newPromotion.getStartDate(), newPromotion.getEndDate());
        CommandUtil.requireNotFreeItem(list,newPromotion.getName());
        double tempPrice = promotionFood.getPrice();
        double newPrice;
        newPrice = (newPromotion.getDiscount() / 100) * tempPrice;
        newPromotion.setPromotionalPrice(newPrice);

        promotionList.add(newPromotion);
        storage.storePromotionList(promotionList);

        return new CommandResult(String.format(MESSAGE_SUCCESS, newPromotion, promotionList.size()));
    }
}
