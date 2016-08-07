package com.adammcneilly.petfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adammcneilly.CoreRecyclerViewAdapter;
import com.adammcneilly.CoreViewHolder;
import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.model.Pet;

import java.util.List;

/**
 * Adapter to display pets for this owner.
 *
 * Created by adam.mcneilly on 8/7/16.
 */
public class PetAdapter extends CoreRecyclerViewAdapter<Pet, PetAdapter.PetViewHolder>{

    public PetAdapter(Context context, List<Pet> pets) {
        super(context, pets);
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PetViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_pet, parent, false));
    }

    public class PetViewHolder extends CoreViewHolder<Pet> {
        private TextView petName;

        public PetViewHolder(View view) {
            super(view);

            petName = (TextView) view.findViewById(R.id.pet_name);
        }

        @Override
        protected void bindItem(Pet item) {
            petName.setText(item.name);
        }
    }
}
