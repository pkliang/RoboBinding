/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.adapterview;

import org.robobinding.presentationmodel.DataSetAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ItemLayoutAttribute implements AdapterViewAttribute
{
	private AdapterViewAttribute itemLayoutAttribute;
	
	public ItemLayoutAttribute(AdapterView<?> adapterView, String attributeValue)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(attributeValue);
		
		if (bindingDetailsBuilder.bindsToStaticResource())
			itemLayoutAttribute = new StaticItemLayoutAttribute(bindingDetailsBuilder.createResourceBindingDetails());
		else
			itemLayoutAttribute = new DynamicItemLayoutAttribute(adapterView, bindingDetailsBuilder.createPropertyBindingDetails());
	}

	@Override
	public void bind(DataSetAdapter<?> dataSetAdapter, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		itemLayoutAttribute.bind(dataSetAdapter, presentationModelAdapter, context);			
	}
	
	protected void updateLayoutId(DataSetAdapter<?> dataSetAdapter, int layoutId)
	{
		dataSetAdapter.setItemLayoutId(layoutId);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private class DynamicItemLayoutAttribute extends AbstractReadOnlyPropertyViewAttribute<AdapterView<?>, Integer> implements AdapterViewAttribute
	{
		private DataSetAdapter<?> dataSetAdapter;
		
		private final AdapterView adapterView;

		public DynamicItemLayoutAttribute(AdapterView<?> adapterView, PropertyBindingDetails propertyBindingDetails)
		{
			this.adapterView = adapterView;
			setPropertyBindingDetails(propertyBindingDetails);
		}
		
		@Override
		public void bind(DataSetAdapter<?> dataSetAdapter, PresentationModelAdapter presentationModelAdapter, Context context)
		{
			this.dataSetAdapter = dataSetAdapter;
			super.bind(presentationModelAdapter, context);
		}

		@Override
		protected void valueModelUpdated(Integer newItemLayout)
		{
			updateLayoutId(dataSetAdapter, newItemLayout);
			adapterView.setAdapter(dataSetAdapter);
		}
	}
	
	private class StaticItemLayoutAttribute implements AdapterViewAttribute
	{
		private ResourceBindingDetails resourceBindingDetails;

		public StaticItemLayoutAttribute(ResourceBindingDetails resourceBindingDetails)
		{
			this.resourceBindingDetails = resourceBindingDetails;
		}

		@Override
		public void bind(DataSetAdapter<?> dataSetAdapter, PresentationModelAdapter presentationModelAdapter, Context context)
		{
			int itemLayoutId = resourceBindingDetails.getResourceId(context);
			updateLayoutId(dataSetAdapter, itemLayoutId);
		}
	}

}