/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.lodz.p.interiordesignapp.arcore;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.core.Config;
import com.google.ar.core.Plane;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

import pl.lodz.p.interiordesignapp.R;
import pl.lodz.p.interiordesignapp.controller.ViewPagerAdapter;
import pl.lodz.p.interiordesignapp.fragment.CategoryFragment;
import pl.lodz.p.interiordesignapp.fragment.RootFragment;
import pl.lodz.p.interiordesignapp.utils.AppConst;

/**
 * Writing Ar Fragment extends the ArFragment class to include the WRITER_EXTERNAL_STORAGE
 * permission. This adds this permission to the list of permissions presented to the user for
 * granting.
 */
public class WritingArFragment extends ArFragment {

    @Override
  public String[] getAdditionalPermissions() {
    String[] additionalPermissions = super.getAdditionalPermissions();
    int permissionLength = additionalPermissions != null ? additionalPermissions.length : 0;
    String[] permissions = new String[permissionLength + 1];
    permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    if (permissionLength > 0) {
      System.arraycopy(additionalPermissions, 0, permissions, 1, additionalPermissions.length);
    }
    return permissions;
  }

  public boolean hasWritePermission() {
    return ActivityCompat.checkSelfPermission(
            this.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED;
  }

  /** Launch Application Setting to grant permissions. */
  public void launchPermissionSettings() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.fromParts("package", requireActivity().getPackageName(), null));
    requireActivity().startActivity(intent);
  }

}
